package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Fresco");
    }

    @OnClick(R.id.bt_fresco_spimg)
    void bt_fresco_spimg(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoSpimgActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_crop)
    void bt_fresco_crop(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoCropActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_circleAndCorner)
    void bt_fresco_circleAndCorner(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoCircleAndCornerActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_jpeg)
    void bt_fresco_jpeg(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoJpegActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_gif)
    void bt_fresco_gif(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoGifActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_multi)
    void bt_fresco_multi(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoMultiActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_listener)
    void bt_fresco_listener(View v){
        Intent view = new Intent(FrescoActivity.this,FrescoListenerActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_fresco_resize)
    void bt_fresco_resize(View v){
        Intent intent = new Intent(FrescoActivity.this,FrescoResizeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_fresco_modifyImg)
    void bt_fresco_modifyImg(View v){
        Intent intent = new Intent(FrescoActivity.this,FrescoModifyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_fresco_autoSizeImg)
    void bt_fresco_autoSizeImg(View v){
        Intent intent = new Intent(FrescoActivity.this,FrescoAutoSizeActivity.class);
        startActivity(intent);
    }


}
