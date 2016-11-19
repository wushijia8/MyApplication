package net.jaris.jaris_code.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.adapter.ImageloaderListviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageloaderListviewActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_imageloader)
    ListView lvImageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader_listview);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("ImageLoader应用在ListView中");
        ImageloaderListviewAdapter imageloaderListviewAdapter = new ImageloaderListviewAdapter(this);
        lvImageloader.setAdapter(imageloaderListviewAdapter);
    }
}
