package net.jaris.jaris_code.glide.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlideActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_glide_base)
    Button btGlideBase;
    @Bind(R.id.bt_glide_recycler)
    Button btGlideRecycler;
    @Bind(R.id.bt_glide_tranformations)
    Button btGlideTranformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Glide");
    }

    @OnClick(R.id.bt_glide_base)
    void bt_glide_base(View v){
        Intent intent = new Intent(GlideActivity.this,GlideBaseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_glide_recycler)
    void bt_glide_recycler(View v){
        Intent view = new Intent(GlideActivity.this,GlideRecyclerViewActivity.class);
        startActivity(view);
    }

    @OnClick(R.id.bt_glide_tranformations)
    void bt_glide_tranformations(View v){
        Toast.makeText(this, "bt_glide_tranformations", Toast.LENGTH_SHORT).show();
    }
}
