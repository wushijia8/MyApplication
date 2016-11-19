package net.jaris.jaris_code.glide.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

import net.jaris.jaris_code.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GlideBaseActivity extends Activity {

    @Bind(R.id.tv_glide_1)
    TextView tvGlide1;
    @Bind(R.id.iv_glide_1)
    ImageView ivGlide1;
    @Bind(R.id.tv_glide_2)
    TextView tvGlide2;
    @Bind(R.id.iv_glide_2)
    ImageView ivGlide2;
    @Bind(R.id.tv_glide_3)
    TextView tvGlide3;
    @Bind(R.id.iv_glide_3)
    ImageView ivGlide3;
    @Bind(R.id.tv_glide_4)
    TextView tvGlide4;
    @Bind(R.id.iv_glide_4)
    ImageView ivGlide4;
    @Bind(R.id.tv_glide_5)
    TextView tvGlide5;
    @Bind(R.id.iv_glide_5)
    ImageView ivGlide5;
    @Bind(R.id.tv_glide_6)
    TextView tvGlide6;
    @Bind(R.id.iv_glide_6)
    ImageView ivGlide6;
    @Bind(R.id.tv_glide_7)
    TextView tvGlide7;
    @Bind(R.id.iv_glide_7)
    ImageView ivGlide7;
    @Bind(R.id.tv_glide_8)
    TextView tvGlide8;
    @Bind(R.id.iv_glide_8)
    ImageView ivGlide8;
    @Bind(R.id.tv_glide_9)
    TextView tvGlide9;
    @Bind(R.id.iv_glide_9)
    ImageView ivGlide9;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private String iUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_base);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Glide基本使用");

        //加载网络图片
        tvGlide1.setText("加载网络图片");
        Glide.with(this).load(iUrl).into(ivGlide1);

        //加载资源图片
        tvGlide2.setText("加载资源图片");
        Glide.with(this).load(R.mipmap.ic_launcher).into(ivGlide2);

        //加载本地图片
        tvGlide3.setText("加载本地图片");
        String path = Environment.getExternalStorageDirectory()
                +"/logo.jpg";
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Glide.with(this).load(uri).into(ivGlide3);

        //加载网络gif
        tvGlide4.setText("加载网络GIF");
        String gifUrl = "http://img5.imgtn.bdimg.com/it/u=2749190246,3857616763&fm=21&gp=0.jpg";
        Glide.with(this).load(gifUrl).placeholder(R.mipmap.ic_launcher)
                .into(ivGlide4);

        //加载本地gif

        tvGlide5.setText("加载本地gif");
        String gifPath = "";
        File gifFile = new File(gifPath);
        Glide.with(this).load(gifFile).placeholder(R.mipmap.ic_launcher).into(ivGlide5);


    }
}
