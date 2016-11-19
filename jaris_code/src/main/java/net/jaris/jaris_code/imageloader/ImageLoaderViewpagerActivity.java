package net.jaris.jaris_code.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.adapter.ImageLoaderViewpagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageLoaderViewpagerActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.vp_imageloader_viewpager)
    ViewPager vpImageloaderViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_viewpager);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("ImageLoader应用在ViewPager中");
        ImageLoaderViewpagerAdapter imageLoaderViewpagerAdapter = new ImageLoaderViewpagerAdapter(this);
        vpImageloaderViewpager.setAdapter(imageLoaderViewpagerAdapter);
        //显示第一个条目
        vpImageloaderViewpager.setCurrentItem(1);
    }
}
