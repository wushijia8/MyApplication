package net.jaris.jaris_code.imageloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageLoaderActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_imageloader_listview)
    Button btnImageloaderListview;
    @Bind(R.id.btn_imageloader_gridview)
    Button btnImageloaderGridview;
    @Bind(R.id.bt_imageloader_viewpager)
    Button btImageloaderViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tvTitle.setText("ImageLoader");
    }

    @OnClick(R.id.btn_imageloader_listview)
    void  btn_imageloader_listview_click(View view){
        Intent intent = new Intent(ImageLoaderActivity.this,ImageloaderListviewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_imageloader_gridview)
    void  btn_imageloader_gridview(View view){
        Intent intent = new Intent(ImageLoaderActivity.this,ImageLoaderGirdviewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_imageloader_viewpager)
    void  bt_imageloader_viewpager(View view){
        Intent intent = new Intent(ImageLoaderActivity.this,ImageLoaderViewpagerActivity.class);
        startActivity(intent);
    }


}
