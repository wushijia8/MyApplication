package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoModifyActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_modify)
    SimpleDraweeView sdvFrescoModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_modify);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("修改图片");
    }

    @OnClick(R.id.bt_fresco_modify)
    void bt_fresco_modify(){

        //在图片上添加网格
        Uri uri = Uri.parse("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        Postprocessor postProcessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "postProcessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                for (int i = 0; i < bitmap.getWidth(); i+=2) {
                    for (int j = 0; j < bitmap.getHeight(); j+=2) {
                        bitmap.setPixel(i,j, Color.RED);
                    }
                }
            }
        };
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postProcessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoModify.getController())
                .setImageRequest(request)
                .build();
        sdvFrescoModify.setController(controller);
    }
}
