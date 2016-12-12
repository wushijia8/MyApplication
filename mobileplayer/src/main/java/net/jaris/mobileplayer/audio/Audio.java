package net.jaris.mobileplayer.audio;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.adapter.VideoPagerAdapter;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.video.SystemVideoPlayer;
import net.jaris.mobileplayer.video.Video;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Audio extends Activity {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.tv_nomeida)
    TextView tvNomeida;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    /**
     * 存放数据集合
     */
    private ArrayList<MediaItem> mediaItems;

    private VideoPagerAdapter videoPagerAdapter;

    final public static int READ_EXTERNAL_STORAGE_REQUEST_CODE = 123;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems != null && mediaItems.size()>0) {
                //设置适配器
                videoPagerAdapter = new VideoPagerAdapter(Audio.this,mediaItems,false);
                listview.setAdapter(videoPagerAdapter);
                tvNomeida.setVisibility(View.GONE);
            }else {
                //没有数据
                tvNomeida.setVisibility(View.VISIBLE);
                tvNomeida.setText("没有发现音频...");
            }
            pbLoading.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        initData();
    }

    private void getActionPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        getDataFromLocal();
    }

    /**
     * 用户确认权限后，回调该方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //加载本地的视频数据
                getDataFromLocal();
            } else {
                Toast.makeText(Audio.this, "当前申请读取外部存储权限已被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initData() {
        getActionPermission();
        //设置点击事件
        listview.setOnItemClickListener(new Audio.MyOnItemClickListener());
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Audio.this,AudioPlayerActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

    /**
     * 从本地的SD卡中得到数据
     *  1.遍历SD卡，根据后缀名
     *  2.从内容提供者里面获取视频
     *
     */
    private void getDataFromLocal() {
        SystemClock.sleep(2000);
        mediaItems = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                super.run();
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

                //发送消息
                handler.sendEmptyMessage(10);

            }
        }.start();
    }

}
