package net.jaris.jaris_code.fresco;

import android.app.Activity;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoCropActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_fresco_explain)
    TextView tvFrescoExplain;
    @Bind(R.id.sdv_fresco_crop)
    SimpleDraweeView sdvFrescoCrop;
    private GenericDraweeHierarchyBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_crop);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("图片的不同裁剪");
        builder = new GenericDraweeHierarchyBuilder(getResources());
    }

    @OnClick(R.id.bt_fresco_center)
    void bt_fresco_center(View view) {
        tvFrescoExplain.setText("居中，无缩放");
        //样式设置，居中，无缩放
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build();
        imageDisplay(hierarchy);
    }

    private void imageDisplay(GenericDraweeHierarchy hierarchy) {
        sdvFrescoCrop.setHierarchy(hierarchy);

        Uri uri = Uri.parse("https://aecpm.alicdn.com/simba/img/TB19ieWNpXXXXauXpXXSutbFXXX.jpg");
        sdvFrescoCrop.setImageURI(uri);
    }


    @OnClick(R.id.bt_fresco_centercrop)
    void bt_fresco_centercrop(View view) {
        //样式设置，保持宽高比缩小或放大，使得两边都大于或等于显示边界，居中显示
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_focuscrop)
    void bt_fresco_focuscrop(View view) {
        //样式设置，同centerCrop，但居中点不是中点，而是指定的某个点，这里设置为图片的左上角0,0
        PointF point = new PointF(0,0);
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).
                setActualImageFocusPoint(point).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_centerinside)
    void bt_fresco_centerinside(View view) {
        //使两边都在显示边界内，居中显示，如果图尺寸大于显示边界，则保持长宽比缩小图片
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_fitcenter)
    void bt_fresco_fitcenter(View view) {
        //保持宽高比，缩小或放大，使得图片完全显示在显示边界内，居中显示
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_fitstart)
    void bt_fresco_fitstart(View view) {
        //保持款高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界左上对齐
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_fitend)
    void bt_fresco_fitend(View view) {
        //保持款高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界右下对齐
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_fitxy)
    void bt_fresco_fitxy(View view) {
        //不保持宽高比，填充满显示边界
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY).build();
        imageDisplay(hierarchy);
    }


    @OnClick(R.id.bt_fresco_none)
    void bt_fresco_none(View view) {
        //
        GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(null).build();
        imageDisplay(hierarchy);
    }


}
