package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoGifActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco_gif)
    SimpleDraweeView sdvFrescoGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_gif);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("GIF动画图片");
    }

    @OnClick(R.id.bt_fresco_askImg)
    void bt_fresco_askImg(View view){
        Uri uri = Uri.parse("http://img2.imgtn.bdimg.com/it/u=1890171434,538245744&fm=21&gp=0.jpg");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)//让gif图片动起来
                .setOldController(sdvFrescoGif.getController())
                .build();
        sdvFrescoGif.setController(controller);
    }

    @OnClick(R.id.bt_fresco_stopAnim)
    void bt_fresco_stopAnim(View view){
        Animatable animatable = sdvFrescoGif.getController().getAnimatable();
        if (animatable != null && animatable.isRunning()) {
            animatable.stop();
        }
    }

    @OnClick(R.id.bt_fresco_startAnim)
    void bt_fresco_startAnim(View view){
        Animatable animatable = sdvFrescoGif.getController().getAnimatable();
        //如果动画不为空且还没开始播放
        if(animatable != null && !animatable.isRunning()){
            animatable.start();
        }
    }


}
