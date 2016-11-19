package net.jaris.jaris_code.picasso.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.picasso.activity.PicassoListViewActivity;
import net.jaris.jaris_code.picasso.activity.PicassoTransfromationsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PicassoActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_picasso_base)
    Button btPicassoBase;
    @Bind(R.id.bt_picasso_listview)
    Button btPicassoListview;
    @Bind(R.id.bt_picasso_tranformations)
    Button btPicassoTranformations;
    @Bind(R.id.iv_picasso_result1)
    ImageView ivPicassoResult1;
    @Bind(R.id.iv_picasso_result2)
    ImageView ivPicassoResult2;
    @Bind(R.id.iv_picasso_result3)
    ImageView ivPicassoResult3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("Picasso");

    }

    @OnClick(R.id.bt_picasso_base)
    void bt_picasso_base(View view){
        //基本用法
        Toast.makeText(this, "基本方法使用", Toast.LENGTH_SHORT).show();
        //普通加载图片
        Picasso.with(PicassoActivity.this)
                .load("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                .into(ivPicassoResult1);
        //裁剪的方式加载图片
        Picasso.with(PicassoActivity.this)
                .load("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                .resize(100,100)
                .into(ivPicassoResult2);
        //旋转180度
        Picasso.with(PicassoActivity.this)
                .load("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                .rotate(180)
                .into(ivPicassoResult3);
    }

    @OnClick(R.id.bt_picasso_listview)
    void bt_picasso_listview(View view){
        Intent intent = new Intent(this, PicassoListViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_picasso_tranformations)
    void bt_picasso_tranformations(View view){
        Intent intent = new Intent(this, PicassoTransfromationsActivity.class);
        startActivity(intent);
    }
}
