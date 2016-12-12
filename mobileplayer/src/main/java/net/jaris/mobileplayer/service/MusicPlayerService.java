package net.jaris.mobileplayer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Toast;

import net.jaris.mobileplayer.IMusicPlayerService;
import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.audio.AudioPlayerActivity;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Jaris on 2016/12/7.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class MusicPlayerService extends Service {

    public static final String OPENAUDIO = "net.jaris.mobileplayer_OPENAUDIO";
    private ArrayList<MediaItem> mediaItems;

    private int position;

    private MediaPlayer mediaPlayer;

    /**
     * 顺序播放
     */
    public static final int REPRAT_NORMAL = 1;

    /**
     * 单曲循环
     */
    public static final int REPRAT_SINGLE = 2;

    /**
     * 列表循环
     */
    public static final int REPRAT_ALL = 3;

    /**
     * 播放模式
     */
    private int playMode = REPRAT_NORMAL;

    private NotificationManager manager;

    /**
     * 当前播放的音频文件对象
     */
    private MediaItem mediaItem;

    private IMusicPlayerService.Stub stub = new IMusicPlayerService.Stub() {

        MusicPlayerService service = MusicPlayerService.this;

        @Override
        public void openAudio(int position) throws RemoteException {
            service.openAudio(position);
        }

        @Override
        public void start() throws RemoteException {
            service.start();
        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public void stop() throws RemoteException {
            service.stop();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return service.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public String getArtist() throws RemoteException {
            return service.getArtist();
        }

        @Override
        public String getName() throws RemoteException {
            return service.getName();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return service.getAudioPath();
        }

        @Override
        public void next() throws RemoteException {
            service.next();
        }

        @Override
        public void pre() throws RemoteException {
            service.pre();
        }

        @Override
        public void setPlayMode(int playMode) throws RemoteException {
            service.setPlayMode(playMode);
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return service.getPlayMode();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return service.isPlaying();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            mediaPlayer.seekTo(position);
        }

        @Override
        public int getAudioSessionId() throws RemoteException {
            return mediaPlayer.getAudioSessionId();
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        playMode = CacheUtils.getPlayMode(this,"playmode");
        //加载音乐列表
        getDataFromLocal();
    }

    private void getDataFromLocal() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mediaItems = new ArrayList<MediaItem>();
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Audio.Media.DISPLAY_NAME,//视频文件在SD卡中的名称
                        MediaStore.Audio.Media.DURATION,//视频的总时长
                        MediaStore.Audio.Media.SIZE,//视频文件的大小
                        MediaStore.Audio.Media.DATA,//视频的绝对地址
                        MediaStore.Audio.Media.ARTIST //歌曲的演唱者
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();

                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//视频的文件大小
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);//视频的播放地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);

                        mediaItems.add(mediaItem);

                    }
                    cursor.close();
                }

            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    /**
     * 根据位置打开对应的音频文件
     * @param position
     */
    private void openAudio(int position){
        this.position = position;
        if (mediaItems != null && mediaItems.size() >0) {
            mediaItem = mediaItems.get(position);
            if (mediaPlayer != null) {

                mediaPlayer.reset();
            }

            try {
                mediaPlayer = new MediaPlayer();
                //设置监听
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                mediaPlayer.setOnErrorListener(new MyOnErrorListener());
                mediaPlayer.setDataSource(mediaItem.getData());
                mediaPlayer.prepareAsync();

                if(playMode == MusicPlayerService.REPRAT_SINGLE){
                    //单曲循环
                    mediaPlayer.setLooping(true);
                }else {
                    //不循环播放
                    mediaPlayer.setLooping(false);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this, "还没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return true;
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }

    class MyOnPreparedListener implements android.media.MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(android.media.MediaPlayer mp) {
            //通知Activity获取信息
            //notifyChange(OPENAUDIO);
            EventBus.getDefault().post(mediaItem);
            start();
        }
    }

    /**
     * 根据动作发广播
     * @param action
     */
    private void notifyChange(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }


    /**
     * 播放音乐
     */
    private void start(){
        mediaPlayer.start();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, AudioPlayerActivity.class);
        intent.putExtra("Notification",true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notification_music_playing)
                .setContentTitle("手机影音")
                .setContentText("正在播放："+getName())
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1,notification);
    }

    /**
     * 暂停音乐
     */
    private void pause(){
        mediaPlayer.pause();
        manager.cancel(1);
    }

    /**
     * 停止
     */
    private void stop(){}

    /**
     * 得到当前播放进度
     * @return
     */
    private int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * 得到当前歌曲的总时长
     * @return
     */
    private int getDuration(){
        return mediaPlayer.getDuration();
    }

    /**
     * 得到艺术家
     * @return
     */
    private String getArtist(){
        return mediaItem.getArtist();
    }

    /**
     * 得到歌曲名称
     * @return
     */
    private String getName(){
        return mediaItem.getName();
    }

    /**
     * 得到歌曲播放的路径
     */
    private String getAudioPath(){
        return mediaItem.getData();
    }

    /**
     * 播放下一首
     */
    private void next(){
        //根据当前的播放模式，设置下一个的位置
        setNextPosition();
        //根据当前的播放模式和下标位置播放音频
        openNextAudio();
    }

    private void openNextAudio() {
        int playMode = getPlayMode();
        if(playMode == MusicPlayerService.REPRAT_NORMAL){
            if(position<mediaItems.size()){
                openAudio(position);
            }else {
                position = mediaItems.size() - 1;
            }
        }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
            openAudio(position);
        }else if(playMode == MusicPlayerService.REPRAT_ALL){
            openAudio(position);
        }else {
            if(position<mediaItems.size()){
                openAudio(position);
            }else {
                position = mediaItems.size() - 1;
            }
        }
    }

    private void setNextPosition() {
        int playMode = getPlayMode();
        if(playMode == MusicPlayerService.REPRAT_NORMAL){
            position++;
        }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
            position++;
            if(position>=mediaItems.size()){
                position = 0;
            }
        }else if(playMode == MusicPlayerService.REPRAT_ALL){
            position++;
            if(position>=mediaItems.size()){
                position = 0;
            }
        }else {
            position++;
        }
    }

    /**
     * 播放上一首
     */
    private void pre(){
        setPrePosition();
        openPrePosition();
    }

    private void openPrePosition() {
        int playMode = getPlayMode();
        if(playMode == MusicPlayerService.REPRAT_NORMAL){
            if(position>=0){
                openAudio(position);
            }else {
                position = 0;
            }
        }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
            openAudio(position);
        }else if(playMode == MusicPlayerService.REPRAT_ALL){
            openAudio(position);
        }else {
            if(position>=0){
                openAudio(position);
            }else {
                position = 0;
            }
        }
    }

    private void setPrePosition() {
        int playMode = getPlayMode();
        if(playMode == MusicPlayerService.REPRAT_NORMAL){
            position--;
        }else if(playMode == MusicPlayerService.REPRAT_SINGLE){
            position--;
            if(position< 0 ){
                position = mediaItems.size() - 1;
            }
        }else if(playMode == MusicPlayerService.REPRAT_ALL){
            position--;
            if(position< 0 ){
                position = mediaItems.size() - 1;
            }
        }else {
            position--;
        }
    }

    /**
     * 设置播放模式
     * @param playMode
     */
    private void setPlayMode(int playMode){
        this.playMode = playMode;
        CacheUtils.putPlayMode(this,"playmode",playMode);
        if(playMode == MusicPlayerService.REPRAT_SINGLE){
            //单曲循环
            mediaPlayer.setLooping(true);
        }else {
            //不循环播放
            mediaPlayer.setLooping(false);
        }
    }

    /**
     * 得到播放模式
     */
    private int getPlayMode(){
        return playMode;
    }

    /**
     * 是否在播放音频
     * @return
     */
    private boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
