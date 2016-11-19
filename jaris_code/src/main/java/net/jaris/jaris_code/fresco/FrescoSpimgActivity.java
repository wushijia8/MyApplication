package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FrescoSpimgActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_spimg)
    SimpleDraweeView sdvFrescoSpimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_spimg);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("带进度条的图片");

        //设置样式
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
        sdvFrescoSpimg.setHierarchy(hierarchy);
        //加载网络图片
        Uri url = Uri.parse("https://aecpm.alicdn.com/simba/img/TB19ieWNpXXXXauXpXXSutbFXXX.jpg");
        sdvFrescoSpimg.setImageURI(url);
    }
}
