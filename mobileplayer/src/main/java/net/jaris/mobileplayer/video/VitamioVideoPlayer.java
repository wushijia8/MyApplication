package net.jaris.mobileplayer.video;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.utils.Utils;
import net.jaris.mobileplayer.view.VitamioVideoView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Jaris on 2016/11/28.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */
public class VitamioVideoPlayer extends Activity implements View.OnClickListener {

    private boolean isUseSystem = false;

    /**
     * 视频进度的更新
     */
    private static final int PROGRESS = 1;

    /**
     * 隐藏控制面板
     */
    private static final int HIDE_MEDIACONTROLLER = 2;

    /**
     * 显示网速
     */
    private static final int SHOW_SPEED = 3;

    /**
     * 全屏
     */
    private static final int FULL_SCREEN = 1;

    /**
     * 默认屏幕
     */
    private static final int DEFAULT_SCREEN = 2;

    @Bind(R.id.videoview)
    VitamioVideoView videoview;

    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button btnVoice;
    private SeekBar seekbarVoice;
    private Button btnSwichPlayer;
    private RelativeLayout mediaController;
    private TextView tvCurrentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button btnExit;
    private Button btnVideoPre;
    private Button btnVideoStartPause;
    private Button btnVideoNext;
    private Button btnVideoSiwchScreen;
    private TextView tvBufferNetSpeed;
    private LinearLayout llBuffer;
    private TextView tvLoadingNetSpeed;
    private LinearLayout llLoading;

    private Utils utils;
    /**
     * 监听电量变化的广播
     */
    private MyReceiver receiver;

    /**
     * 传递的视频列表
     */
    private ArrayList<MediaItem> mediaItems;

    /**
     * 要播放列表中的具体位置
     */
    private int position;

    /**
     * 定义手势识别器
     */
    private GestureDetector detector;

    /**
     * 是否显示控制面板
     */
    private boolean isShowController;

    /**
     * 是否全屏
     */
    private boolean isFullScreen = false;

    /**
     * 屏幕的宽度
     */
    private int screenWidth = 0;

    /**
     * 屏幕的高
     */
    private int screenHeight;

    /**
     * 真实视频的宽和高
     */
    private int videoWidth;
    private int videoHeight;

    /**
     * 调节声音
     */
    private AudioManager am;

    /**
     * 当前音量
     */
    private int currentVoice;

    /**
     * 最大音量：0~15
     */
    private int maxVoice;

    /**
     *默认情况下是否静音
     */
    private boolean isMute = false;

    /**
     * 是否是网络的URI
     */
    private boolean isNetUri;

    /**
     * 上一次的播放进度
     */
    private int prePosition;

    private void findViews(){
        tvName = (TextView) findViewById(R.id.tv_name);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvSystemTime = (TextView) findViewById(R.id.tv_system_time);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwichPlayer = (Button) findViewById(R.id.btn_swich_player);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        seekbarVideo = (SeekBar) findViewById(R.id.seekbar_video);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnVideoPre = (Button) findViewById(R.id.btn_video_pre);
        btnVideoStartPause = (Button) findViewById(R.id.btn_video_start_pause);
        btnVideoNext = (Button) findViewById(R.id.btn_video_next);
        btnVideoSiwchScreen = (Button) findViewById(R.id.btn_video_siwch_screen);
        mediaController = (RelativeLayout) findViewById(R.id.media_controller);
        tvBufferNetSpeed = (TextView) findViewById(R.id.tv_buffer_netspeed);
        llBuffer = (LinearLayout) findViewById(R.id.ll_buffer);
        tvLoadingNetSpeed = (TextView) findViewById(R.id.tv_loading_netspeed);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);

        btnVoice.setOnClickListener(this);
        btnSwichPlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnVideoPre.setOnClickListener(this);
        btnVideoStartPause.setOnClickListener(this);
        btnVideoNext.setOnClickListener(this);
        btnVideoSiwchScreen.setOnClickListener(this);

        seekbarVoice.setMax(maxVoice);
        //设置当前的进度
        seekbarVoice.setProgress(currentVoice);

        //开始更新网速
        handler.sendEmptyMessage(SHOW_SPEED);

    }

    private Uri uri;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_SPEED://显示网速
                    //得到网速
                    String netSpeed = utils.getNetSpeed(VitamioVideoPlayer.this);
                    //显示网络速度
                    tvLoadingNetSpeed.setText("玩命加载中..."+netSpeed);
                    tvBufferNetSpeed.setText("缓冲中..."+netSpeed);
                    //两秒钟调用一次
                    handler.removeMessages(SHOW_SPEED);
                    handler.sendEmptyMessageDelayed(SHOW_SPEED,2000);
                    break;
                case HIDE_MEDIACONTROLLER:
                    hideMediaController();
                    break;
                case PROGRESS:
                    //得到当前视频播放的进度
                    int currentPosition = (int) videoview.getCurrentPosition();
                    seekbarVideo.setProgress(currentPosition);

                    //更新文本播放进度
                    tvCurrentTime.setText(utils.stringForTime(currentPosition));

                    //设置系统时间
                    tvSystemTime.setText(getSystemTime());

                    //缓存进度的更新
                    if(isNetUri){
                        //只有网络资源才有缓存效果
                        int buffer = videoview.getBufferPercentage();
                        int totalBuffer = buffer * seekbarVideo.getMax();
                        int secondaryProgress = totalBuffer / 100;
                        seekbarVideo.setSecondaryProgress(secondaryProgress);
                    }else {
                        //本地视频没有缓存效果
                        seekbarVideo.setSecondaryProgress(0);
                    }

                    //监听卡
                    if(!isUseSystem && videoview.isPlaying()){

                        if(videoview.isPlaying()){
                            int buffer = currentPosition - prePosition;
                            if(buffer < 500){
                                //视频卡顿了
                                llBuffer.setVisibility(View.VISIBLE);
                            }else {
                                llBuffer.setVisibility(View.GONE);
                            }
                        }else {
                            llBuffer.setVisibility(View.GONE);
                        }
                    }

                    prePosition = currentPosition;

                    //每秒更新一次
                    removeMessages(PROGRESS);//移除消息
                    sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.N)
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        setContentView(R.layout.activity_vitamio_video_player);
        ButterKnife.bind(this);

        initData();

        findViews();
        setListener();
        getData();

        setData();

        //设置控制面板
        //videoview.setMediaController(new MediaController(this));
    }

    private void setData() {

        if (mediaItems != null && mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(position);
            //设置视频的名称
            tvName.setText(mediaItem.getName());
            isNetUri = utils.isNetUri(mediaItem.getData());
            videoview.setVideoPath(mediaItem.getData());
        }else if(uri !=null){
            tvName.setText(uri.toString());
            isNetUri = utils.isNetUri(uri.toString());
            videoview.setVideoURI(uri);
        }else {
            Toast.makeText(this, "没有传递数据", Toast.LENGTH_SHORT).show();
        }
        setButtonState();

    }

    private void getData() {
        //得到播放地址
        uri = getIntent().getData();
        mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position",0);

    }

    private void initData() {
        utils = new Utils();
        //注册电量广播
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //当电量变化时发送广播
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,intentFilter);

        //实例化手势识别器并重写相关方法
        detector = new GestureDetector(this,new MySimpleOnGuestureListener());

        //得到屏幕的宽和高
        //过时的方法：getWindowManager().getDefaultDisplay().getWidth();

        //新的方式
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //得到音量
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        currentVoice = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        maxVoice = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    class MySimpleOnGuestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            //Toast.makeText(SystemVideoPlayer.this, "长按", Toast.LENGTH_SHORT).show();
            startAndPause();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Toast.makeText(SystemVideoPlayer.this, "双击", Toast.LENGTH_SHORT).show();
            setFullScreenAndDefault();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //Toast.makeText(SystemVideoPlayer.this, "单击", Toast.LENGTH_SHORT).show();
            if(isShowController){
                hideMediaController();
                //把隐藏消息移除
                handler.removeMessages(HIDE_MEDIACONTROLLER);
            }else {
                showMediaController();
                //发消息隐藏
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
            }
            return super.onSingleTapConfirmed(e);
        }

    }


    private void setFullScreenAndDefault() {
        if(isFullScreen){
            setVideoType(DEFAULT_SCREEN);
        }else {
            setVideoType(FULL_SCREEN);
        }
    }

    private void setVideoType(int defaultScreen) {
        switch (defaultScreen){
            case FULL_SCREEN:
                //全屏
                //1.设置视频画面的大小，屏幕有多大就设多大
                videoview.setVideoSize(screenWidth,screenHeight);
                //2.设置按钮的状态
                btnVideoSiwchScreen.setBackgroundResource(R.drawable.btn_video_siwch_screen_default_selector);
                isFullScreen = true;
                break;
            case DEFAULT_SCREEN:
                //默认
                //1.设置视频画面的大小
                //视频真实的宽和高
                int mVideoWidth = videoWidth;
                int mVideoHeight = videoHeight;
                //屏幕的宽
                int width = screenWidth;
                int height = screenHeight;
                if(mVideoWidth * height < width * mVideoHeight){
                    width = height * mVideoWidth / mVideoHeight;
                }else if(mVideoWidth * height > width * mVideoHeight){
                    height = width * mVideoHeight / mVideoWidth;
                }
                videoview.setVideoSize(width,height);
                //2.设置按钮的状态
                btnVideoSiwchScreen.setBackgroundResource(R.drawable.btn_video_siwch_screen_full_selector);
                isFullScreen = false;
                break;
        }
    }

    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level",0);
            setBattery(level);
        }
    }

    private void setBattery(int level) {
        if(level <= 0){
            ivBattery.setImageResource(R.drawable.ic_battery_0);
        }else if(level <= 10){
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        }else if(level <= 20){
            ivBattery.setImageResource(R.drawable.ic_battery_20);
        }else if(level <= 40){
            ivBattery.setImageResource(R.drawable.ic_battery_40);
        }else if(level <= 60){
            ivBattery.setImageResource(R.drawable.ic_battery_60);
        }else if (level <= 80){
            ivBattery.setImageResource(R.drawable.ic_battery_80);
        }else if (level <= 100){
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    private void setListener() {
        //准备好的监听
        videoview.setOnPreparedListener(new MyOnPreparedListener());

        //播放出错的监听
        videoview.setOnErrorListener(new MyOnErrorListener());
        //播放完成的监听
        videoview.setOnCompletionListener(new MyOnCompltetionListener());

        //设置SeekBar状态变化的监听
        seekbarVideo.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());

        seekbarVoice.setOnSeekBarChangeListener(new VoiceOnSeekBarChangeListener());

        if(isUseSystem){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                //监听视频播放卡
                videoview.setOnInfoListener(new MyOnInfoListener());
            }
        }
    }

    class MyOnInfoListener implements MediaPlayer.OnInfoListener{

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what){
                case MediaPlayer.MEDIA_INFO_BUFFERING_START://视频卡：拖动卡
                    //Toast.makeText(SystemVideoPlayer.this, "卡了", Toast.LENGTH_SHORT).show();
                    llBuffer.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END://拖动卡结束
                    //Toast.makeText(SystemVideoPlayer.this, "卡结束", Toast.LENGTH_SHORT).show();
                    llBuffer.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }

    class VoiceOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                if(progress > 0){
                    isMute = false;
                }else {
                    isMute = true;
                }
                updateVoice(progress,isMute);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_MEDIACONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
        }
    }

    /**
     * 设置音量的大小
     * @param progress
     */
    private void updateVoice(int progress,boolean isMute) {
        if(isMute){
            am.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            seekbarVoice.setProgress(0);
        }else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            seekbarVoice.setProgress(progress);
            currentVoice = progress;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnVoice) {
            isMute = !isMute;
            updateVoice(currentVoice,isMute);
        } else if (v == btnSwichPlayer) {
            showSwithcPlayDialog();
        } else if (v == btnExit) {
            // Handle clicks for btnExit
            finish();
        } else if (v == btnVideoPre) {
            playPreVideo();
        } else if (v == btnVideoStartPause) {
            startAndPause();
        } else if (v == btnVideoNext) {
            playNextVideo();
        } else if (v == btnVideoSiwchScreen) {
            setFullScreenAndDefault();
        }

        handler.removeMessages(HIDE_MEDIACONTROLLER);
        handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);

    }

    private void showSwithcPlayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("万能播放器提醒您");
        builder.setMessage("当您播放一个视频，有花屏时，可以尝试使用系统播放器播放");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startSystemPlayer();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void startSystemPlayer() {
        if(videoview!=null){
            videoview.stopPlayback();
        }

        Intent intent = new Intent(this,SystemVideoPlayer.class);
        if(mediaItems!=null && mediaItems.size()>0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("videolist",mediaItems);
            intent.putExtras(bundle);
            intent.putExtra("position",position);
        }else if(uri!=null) {
            intent.setData(uri);
        }
        startActivity(intent);
        finish();//关闭页面
    }

    /**
     * 播放上一个视频
     */
    private void playPreVideo() {
        if (mediaItems != null && mediaItems.size() > 0) {
            //播放上一个
            position--;
            if(position >= 0){
                MediaItem mediaItem = mediaItems.get(position);
                tvName.setText(mediaItem.getName());
                isNetUri = utils.isNetUri(mediaItem.getData());
                videoview.setVideoPath(mediaItem.getData());

                //设置按钮状态
                setButtonState();
            }
        }else if (uri != null){
            //设置按钮状态：上一个和下一个按钮设置灰色且不可点击
            setButtonState();
        }
    }

    /**
     * 播放下一个视频
     */
    private void playNextVideo() {
        if (mediaItems != null && mediaItems.size() > 0) {
            //播放下一个
            position++;
            if(position < mediaItems.size()){
                llLoading.setVisibility(View.VISIBLE);
                MediaItem mediaItem = mediaItems.get(position);
                tvName.setText(mediaItem.getName());
                isNetUri = utils.isNetUri(mediaItem.getData());
                videoview.setVideoPath(mediaItem.getData());

                //设置按钮状态
                setButtonState();
            }
        }else if (uri != null){
            //设置按钮状态：上一个和下一个按钮设置灰色且不可点击
            setButtonState();
        }
    }

    private void setButtonState() {
        if (mediaItems != null && mediaItems.size() > 0) {
            if(mediaItems.size()==1){
                setEnable(false);

            }else if(mediaItems.size() == 2){
                if(position == 0){
                    btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                    btnVideoPre.setEnabled(false);

                    btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
                    btnVideoNext.setEnabled(true);


                }else if(position == mediaItems.size() - 1){
                    btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                    btnVideoNext.setEnabled(false);

                    btnVideoPre.setBackgroundResource(R.drawable.btn_video_pre_selector);
                    btnVideoPre.setEnabled(true);
                }
            }else {
                if(position == 0){
                    btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                    btnVideoPre.setEnabled(false);
                }else if(position == mediaItems.size() - 1){
                    btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                    btnVideoNext.setEnabled(false);
                }else {
                    setEnable(true);
                }
            }
        }else if(uri != null){
            //两个按钮设置灰色
            setEnable(false);
        }
    }

    private void setEnable(boolean isEnable) {

        if (isEnable) {
            btnVideoPre.setBackgroundResource(R.drawable.btn_video_pre_selector);
            btnVideoPre.setEnabled(true);
            btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
            btnVideoNext.setEnabled(true);
        } else{
            //两个按钮设置灰色btnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
        btnVideoPre.setEnabled(false);
        btnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
        btnVideoNext.setEnabled(false);
        }
    }

    private void startAndPause() {
        if (videoview.isPlaying()) {
            //视频在播放-设置暂停
            videoview.pause();
            //按钮状态设置播放
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_start_selector);
        } else {
            //视频播放
            videoview.start();
            //按钮状态设置暂停
            btnVideoStartPause.setBackgroundResource(R.drawable.btn_video_pause_selector);
        }
    }

    class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        /**
         * 当手指拖动时，会引起SeekBar进度的变化，会回调该方法
         * @param seekBar
         * @param progress
         * @param fromUser:判断进度条改变时，是否是用户操作的
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                videoview.seekTo(progress);
            }
        }

        /**
         * 手指触碰时回调该方法
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_MEDIACONTROLLER);
        }

        /**
         * 当手指离开时回调该方法
         * @param seekBar
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        /**
         * 当底层解码准备好时调用该方法
         * @param mp
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            videoWidth = mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            //开始播放
            videoview.start();
            //视频总时长，关联总长度
            int duration = (int) videoview.getDuration();
            seekbarVideo.setMax(duration);
            tvDuration.setText(utils.stringForTime(duration));
            hideMediaController();//默认是隐藏控制面板
            //发消息
            handler.sendEmptyMessage(PROGRESS);
            //videoview.setVideoSize(mp.getVideoWidth(),mp.getVideoHeight());
            //屏幕的默认播放
            setVideoType(DEFAULT_SCREEN);

            //把加载的页面消失掉
            llLoading.setVisibility(View.GONE);

            mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Toast.makeText(VitamioVideoPlayer.this, "拖动完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            showErrorDialog();
            return true;//返回false自动弹出播放错误对话框
        }
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("抱歉，无法播放该视频！！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    class MyOnCompltetionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            playNextVideo();
            //Toast.makeText(SystemVideoPlayer.this, "播放完成了："+uri, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacksAndMessages(null);

        //释放资源时先释放子类再释放父类
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    private float startY;
    /**
     * 屏幕的高
     */
    private float touchRang;

    /**
     * 当一按下的音量
     */
    private int mVol;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                mVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                touchRang = Math.min(screenHeight,screenWidth);
                handler.removeMessages(HIDE_MEDIACONTROLLER);
                break;
            case MotionEvent.ACTION_MOVE:
                //移动时记录相关值
                float endY = event.getY();
                float distanceY = startY - endY;
                float delta = (distanceY / touchRang) * maxVoice;
                int voice = (int) Math.min(Math.max(mVol+delta,0),maxVoice);
                if(delta != 0){
                    isMute = false;
                    updateVoice(voice,isMute);
                }
                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 显示控制面板
     */
    private void showMediaController(){
        mediaController.setVisibility(View.VISIBLE);
        isShowController = true;
    }

    /**
     * 隐藏控制面板
     */
    private void hideMediaController(){
        mediaController.setVisibility(View.GONE);
        isShowController = false;
    }

    /**
     * 监听物理键实现声音的调节大小
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            currentVoice --;
            updateVoice(currentVoice,false);
            handler.removeMessages(HIDE_MEDIACONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            currentVoice ++;
            updateVoice(currentVoice,false);
            handler.removeMessages(HIDE_MEDIACONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER,4000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
