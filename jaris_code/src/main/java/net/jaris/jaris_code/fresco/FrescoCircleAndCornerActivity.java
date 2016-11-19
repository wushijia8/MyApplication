package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoCircleAndCornerActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_circleandcorner)
    SimpleDraweeView sdvFrescoCircleandcorner;
    private Uri uri;
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_circle_and_corner);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("圆角图片");

        uri = Uri.parse("https://aecpm.alicdn.com/simba/img/TB19ieWNpXXXXauXpXXSutbFXXX.jpg");
        builder = new GenericDraweeHierarchyBuilder(getResources());

    }

    @OnClick(R.id.bt_fresco_circle)
    void bt_fresco_circle(View view){
        //设置圆形图片
        params = RoundingParams.asCircle();
        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(params).build();
        sdvFrescoCircleandcorner.setHierarchy(hierarchy);
        sdvFrescoCircleandcorner.setImageURI(uri);
    }

    @OnClick(R.id.bt_fresco_corner)
    void bt_fresco_corner(View view){
        //设置圆角图片

        params = RoundingParams.fromCornersRadius(50f);

        //覆盖层
        params.setOverlayColor(getResources().getColor(android.R.color.holo_red_light));
        //边框
        params.setBorder(getResources().getColor(android.R.color.holo_blue_light),5);
        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(params).build();
        sdvFrescoCircleandcorner.setHierarchy(hierarchy);
        //加载图片
        sdvFrescoCircleandcorner.setImageURI(uri);
    }
}
