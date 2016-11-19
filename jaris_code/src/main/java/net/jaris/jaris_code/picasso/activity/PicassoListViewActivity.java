package net.jaris.jaris_code.picasso.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.picasso.adapter.PicassoListViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PicassoListViewActivity extends Activity {

    @Bind(R.id.lv_picasso)
    ListView lvPicasso;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_list_view);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Picasso在ListView中使用");

        PicassoListViewAdapter picassoListViewAdapter = new PicassoListViewAdapter(this);
        lvPicasso.setAdapter(picassoListViewAdapter);
    }
}
