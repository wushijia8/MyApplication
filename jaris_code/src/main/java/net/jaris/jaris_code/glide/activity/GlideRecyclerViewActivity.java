package net.jaris.jaris_code.glide.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.glide.adapter.GlideRecyclerViewAdapater;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GlideRecyclerViewActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_glide)
    RecyclerView rvGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_recycler_view);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Glide在RecyclerView中加载图片");

        //初始化RecyclerView
        GlideRecyclerViewAdapater glideRecyclerViewAdapater = new GlideRecyclerViewAdapater(this);
        rvGlide.setAdapter(glideRecyclerViewAdapater);

        rvGlide.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}
