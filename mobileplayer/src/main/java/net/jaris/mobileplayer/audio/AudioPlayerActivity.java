package net.jaris.mobileplayer.audio;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.mobileplayer.IMusicPlayerService;
import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.service.MusicPlayerService;
import net.jaris.mobileplayer.utils.LyricUtils;
import net.jaris.mobileplayer.utils.Utils;
import net.jaris.mobileplayer.view.ShowLyricView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.ButterKnife;

public class AudioPlayerActivity extends Activity implements View.OnClickListener {

   /* @Bind(R.id.iv_icon)
    ImageView ivIcon;*/

    /**
     * 进度更新
     */
    private static final int PROGRESS = 1;

    /**
     * 显示歌词
     */
    private static final int SHOW_LYRC = 2;

    private int position;
    /**
     * true：从状态栏进入的
     */
    private boolean notification;

    private IMusicPlayerService service;//服务的代理类，通过该类的对象可以获得服务的方法

    private TextView tvArtist;
    private TextView tvName;
    private TextView tvTime;
    private SeekBar seekbarAudio;
    private Button btnAudioPlaymode;
    private Button btnAudioPre;
    private Button btnAudioStartPause;
    private Button btnAudioNext;
    private Button btnLyrc;
    private ShowLyricView showLyricView;

    private MyReceiver myReceiver;
    private Utils utils;

    private Visualizer mVisualizer;

    private void findViews() {
        /*ivIcon.setBackgroundResource(R.drawable.animation_list);

        AnimationDrawable animationDrawable = (AnimationDrawable) ivIcon.getBackground();
        animationDrawable.start();

        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivIcon.setBackgroundResource(R.drawable.animation_list);
        AnimationDrawable rocketAnimation = (AnimationDrawable) ivIcon.getBackground();
        rocketAnimation.start();*/
        tvArtist = (TextView) findViewById(R.id.tv_artist);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        seekbarAudio = (SeekBar) findViewById(R.id.seekbar_audio);
        btnAudioPlaymode = (Button) findViewById(R.id.btn_audio_playmode);
        btnAudioPre = (Button) findViewById(R.id.btn_audio_pre);
        btnAudioStartPause = (Button) findViewById(R.id.btn_audio_start_pause);
        btnAudioNext = (Button) findViewById(R.id.btn_audio_next);
        btnLyrc = (Button) findViewById(R.id.btn_lyrc);
        showLyricView = (ShowLyricView) findViewById(R.id.showLyricView);

        btnAudioPlaymode.setOnClickListener(this);
        btnAudioPre.setOnClickListener(this);
        btnAudioStartPause.setOnClickListener(this);
        btnAudioNext.setOnClickListener(this);
        btnLyrc.setOnClickListener(this);

        //设置视频的拖动
        seekbarAudio.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());

    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                try {
                    service.seekTo(progress);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_LYRC:
                    //得到当前的进度
                    try {
                        int currentPosition = service.getCurrentPosition();
                        //把进度传入ShowLyrcView控件，并计算该高亮哪一句
                        showLyricView.setShowNextLyric(currentPosition);

                        //实时的发消息
                        handler.removeMessages(SHOW_LYRC);
                        handler.sendEmptyMessage(SHOW_LYRC);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case PROGRESS:
                    try {
                        //得到当前进度
                        int currentPosition = service.getCurrentPosition();
                        //设置SeekBar的进度
                        seekbarAudio.setProgress(currentPosition);
                        //设置时间进度更新
                        tvTime.setText(utils.stringForTime(currentPosition)+"/"+utils.stringForTime(service.getDuration()));
                        //每秒更新一次
                        handler.removeMessages(PROGRESS);
                        //延时一秒发一次消息
                        handler.sendEmptyMessageDelayed(PROGRESS,1000);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        ButterKnife.bind(this);

        initData();
        findViews();
        getData();
        bindAndStartService();
    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            service = IMusicPlayerService.Stub.asInterface(iBinder);
            if (service != null) {
                try {
                    if(!notification){//从列表进入
                        service.openAudio(position);
                    }else {//从状态栏进入
                        showViewData();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                if (service != null) {
                    service.stop();
                    service = null;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };


    private void initData() {
        utils = new Utils();
        /*myReceiver = new MyReceiver();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicPlayerService.OPENAUDIO);
        registerReceiver(myReceiver,intentFilter);*/

        //①EventBus注册
        EventBus.getDefault().register(this);
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            showData(null);
        }
    }

    //③订阅方法
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false,priority = 0)
    public void showData(MediaItem mediaItem) {

        //发消息开始同步歌词
        showLyrc();

        showViewData();
        checkPlayMode();
    }


    private void showLyrc() {

        //解析歌词
        LyricUtils lyricUtils = new LyricUtils();
        //解析歌词文件
        try {
            String path = service.getAudioPath();
            //传歌词文件
            path = path.substring(0,path.lastIndexOf("."));
            File file = new File(path + ".lrc");
            if(!file.exists()){
                file = new File(path + ".txt");
            }
            lyricUtils.readLyricFile(file);//解析歌词

            showLyricView.setLyrics(lyricUtils.getLyrics());

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(lyricUtils.isExistsLyric()){
            handler.sendEmptyMessage(SHOW_LYRC);
        }
    }

    private void showViewData() {
        try {
            tvArtist.setText(service.getArtist());
            tvName.setText(service.getName());
            //设置进度条的最大值
            seekbarAudio.setMax(service.getDuration());
            //发消息
            handler.sendEmptyMessage(PROGRESS);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindAndStartService() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.setAction("net.jaris.mobilePlayer_OPENAUDIO");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);//不至于实例化多个服务对象
    }

    /**
     * 得到数据
     */
    private void getData() {
        notification = getIntent().getBooleanExtra("Notification",false);
        if(!notification){
            position = getIntent().getIntExtra("position", 0);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnAudioPlaymode){
            setPlayMode();
        }else if(v == btnAudioPre){
            if (service != null) {
                try {
                    service.pre();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }else if(v == btnAudioStartPause){
            if (service != null) {
                try {
                    if(service.isPlaying()){
                        service.pause();
                        btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_start_selector);
                    }else {
                        service.start();
                        btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_pause_selector);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }else if(v == btnAudioNext){
            if (service != null) {
                try {
                    service.next();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPlayMode() {
        try {
            int playMode = service.getPlayMode();
            if(playMode == MusicPlayerService.REPRAT_NORMAL){
                playMode = MusicPlayerService.REPRAT_SINGLE;
            }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
                playMode = MusicPlayerService.REPRAT_ALL;
            }else if(playMode == MusicPlayerService.REPRAT_ALL){
                playMode = MusicPlayerService.REPRAT_NORMAL;
            }else {
                playMode = MusicPlayerService.REPRAT_NORMAL;
            }

            //保存
            service.setPlayMode(playMode);

            //设置图片
            showPlayMode();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showPlayMode() {
        try {
            int playMode = service.getPlayMode();
            if(playMode == MusicPlayerService.REPRAT_NORMAL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(this, "顺序播放", Toast.LENGTH_SHORT).show();
            }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
                Toast.makeText(this, "单曲循环", Toast.LENGTH_SHORT).show();
            }else if(playMode == MusicPlayerService.REPRAT_ALL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
                Toast.makeText(this, "列表循环", Toast.LENGTH_SHORT).show();
            }else {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(this, "顺序播放", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 校验状态
     */
    private void checkPlayMode() {
        try {
            int playMode = service.getPlayMode();
            if(playMode == MusicPlayerService.REPRAT_NORMAL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
            }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
            }else if(playMode == MusicPlayerService.REPRAT_ALL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
            }else {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
            }

            //检验播放和暂停的按钮
            if(service.isPlaying()){
                btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_start_selector);
            }else {
                btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_pause_selector);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
       /* if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }*/

        //②EventBus取消注册
        EventBus.getDefault().unregister(this);

        //解绑服务
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVisualizer != null) {
            mVisualizer.release();
        }
    }
}
