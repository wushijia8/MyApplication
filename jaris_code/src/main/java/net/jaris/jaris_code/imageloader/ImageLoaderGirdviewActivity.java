package net.jaris.jaris_code.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.adapter.ImageLoaderGridviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageLoaderGirdviewActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.gv_imageloader_gridview)
    GridView gvImageloaderGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_girdview);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Imageloader应用在Gridview中");
        ImageLoaderGridviewAdapter imageLoaderGridviewAdapter = new ImageLoaderGridviewAdapter(this);
        gvImageloaderGridview.setAdapter(imageLoaderGridviewAdapter);
    }
}
