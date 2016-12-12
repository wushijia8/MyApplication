package net.jaris.mobileplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.jaris.mobileplayer.audio.Audio;
import net.jaris.mobileplayer.netvideo.NetVideoActivity;
import net.jaris.mobileplayer.video.Video;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.video)
    Button video;
    @Bind(R.id.audio)
    Button audio;
    @Bind(R.id.net_video)
    Button netVideo;
    @Bind(R.id.net_audio)
    Button netAudio;
    @Bind(R.id.search)
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        video.setOnClickListener(this);
        audio.setOnClickListener(this);
        netVideo.setOnClickListener(this);
        netAudio.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video:
                Intent video = new Intent(this, Video.class);
                startActivity(video);
                break;
            case R.id.audio:
                Intent audio = new Intent(this, Audio.class);
                startActivity(audio);
                break;
            case R.id.net_video:
                Intent netVideo = new Intent(this, NetVideoActivity.class);
                startActivity(netVideo);
                break;
            case R.id.net_audio:
                break;
            case R.id.search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
