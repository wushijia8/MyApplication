package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.jaris.jaris_code.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoMultiActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_multi)
    SimpleDraweeView sdvFrescoMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_multi);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("多图请求及图片复用");
    }

    @OnClick(R.id.bt_fresco_multiImg)
    void bt_fresco_multiImg(View view){
        //先显示低分辨率的图，再显示高分辨率的图

        //同一张图，不同品质的两个URI
        Uri lowResUri = Uri.parse("");
        Uri highResUri = Uri.parse("");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                .setImageRequest(ImageRequest.fromUri(highResUri))
                .build();
        sdvFrescoMulti.setController(controller);
    }

     @OnClick(R.id.bt_fresco_thubnailImg)
    void bt_fresco_thubnailImg(View view){

         //本地缩略图预览
         //设置图片地址
         File file = new File(Environment.getExternalStorageDirectory()+"/mn1.jpg");
         Uri uri = Uri.fromFile(file);
         //加载图片的请求
         ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                 .setLocalThumbnailPreviewsEnabled(true)//设置缩略图
                 .build();
         //控制图片的加载
         DraweeController controller = Fresco.newDraweeControllerBuilder()
                 .setImageRequest(request)
                 .build();
         //加载图片
         sdvFrescoMulti.setController(controller);
    }

    @OnClick(R.id.bt_fresco_multiplexImg)
    void bt_fresco_multiplexImg(View view){
        /*
         本地图片的复用
            在请求图片之前，还会在内存中请求一次图片，没有才会去本地，最后去请求网络URI
            本地准备复用图片的URI，如果这个本地图片不存在，会自动去加载下一个URI
         */
        //请求加载图片
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"mn1.jpg"));

        Uri uri2 = Uri.parse("https://aecpm.alicdn.com/simba/img/TB19ieWNpXXXXauXpXXSutbFXXX.jpg");
        ImageRequest request = ImageRequest.fromUri(uri);
        ImageRequest request2 = ImageRequest.fromUri(uri2);

        ImageRequest[] requests = {request,request2};

        //控制加载图片
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setFirstAvailableImageRequests(requests)
                .setOldController(sdvFrescoMulti.getController())
                .build();

        //加载图片
        sdvFrescoMulti.setController(controller);

    }


}
