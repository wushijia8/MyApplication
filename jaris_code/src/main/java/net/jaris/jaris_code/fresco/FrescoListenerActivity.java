package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoListenerActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_listener)
    SimpleDraweeView sdvFrescoListener;
    @Bind(R.id.tv_fresco_listener)
    TextView tvFrescoListener;
    private ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
        //加载图片完毕
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);

            if (imageInfo == null) {
                return;
            }

            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            tvFrescoListener.setText("Final image received!" +
                    "\nSize:" + imageInfo.getWidth()
                    + "x" + imageInfo.getHeight()
                    +"\nQuality level:" + qualityInfo.getQuality()
                    +"\ngood enough:" + qualityInfo.isOfGoodEnoughQuality()
                    +"\nfull quality:" + qualityInfo.isOfFullQuality());

        }

        //渐进式加载图片回调
        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            super.onIntermediateImageSet(id, imageInfo);

            tvFrescoListener.setText("IntermediateImageSet image receiced");
        }

        //加载图片失败
        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            tvFrescoListener.setText("Error load:" + id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_listener);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("图片加载监听");
    }

    @OnClick(R.id.bt_fresco_listener)
    void bt_fresco_listener(View view) {
        Uri uri = Uri.parse("https://aecpm.alicdn.com/simba/img/TB19ieWNpXXXXauXpXXSutbFXXX.jpg");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoListener.getController())
                .setImageRequest(request)
                .setControllerListener(controllerListener)
                .build();
        sdvFrescoListener.setController(controller);
    }
}
