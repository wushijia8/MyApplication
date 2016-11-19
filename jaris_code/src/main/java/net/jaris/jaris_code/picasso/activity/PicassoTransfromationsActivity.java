package net.jaris.jaris_code.picasso.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.picasso.adapter.PicassoTransformationsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PicassoTransfromationsActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_picasso_transformations)
    ListView lvPicassoTransformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_transfromations);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Picasso的转换操作");

        List<String> data = new ArrayList<>();

        for (int i = 0; i <= 36; i++) {
            data.add(i+"");
        }

        PicassoTransformationsAdapter picassoTransformationsAdapter = new PicassoTransformationsAdapter(PicassoTransfromationsActivity.this,data);
        lvPicassoTransformations.setAdapter(picassoTransformationsAdapter);

    }
}
