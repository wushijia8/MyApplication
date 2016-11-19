package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoResizeActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_resize)
    SimpleDraweeView sdvFrescoResize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_resize);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("图片缩放和旋转");
    }

    @OnClick(R.id.bt_fresco_resize)
    void bt_fresco_resize(){
        //修改内存中图片的大小
        //图片地址
        Uri uri = Uri.parse("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        //图片请求
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(50,50))
                .build();
        //控制图片的加载
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoResize.getController())
                .setImageRequest(request)
                .build();
        //加载图片
        sdvFrescoResize.setController(controller);
    }

    @OnClick(R.id.bt_fresco_rotate)
    void bt_fresco_rotate(){

        Uri uri = Uri.parse("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)//设置自动旋转
                .build();
        //控制图片加载
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoResize.getController())
                .setImageRequest(request)
                .build();
        sdvFrescoResize.setController(controller);
    }
}
