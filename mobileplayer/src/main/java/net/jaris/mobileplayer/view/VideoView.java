package net.jaris.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Jaris on 2016/11/29.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class VideoView extends android.widget.VideoView{

    /**
     * 在代码中创建时用该方法
     * @param context
     */
    public VideoView(Context context) {
        this(context,null);
    }

    /**
     * 当这个类在布局文件的时候，系统通过该构造方法实例化该类
     * @param context
     * @param attrs
     */
    public VideoView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    /**
     * 当需要设置样式的时候调用该方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 设置视频的宽和高
     *
     * @param videoWidth：指定视频的宽
     * @param videoHeight：指定视频的高
     */
    public void setVideoSize(int videoWidth,int videoHeight){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = videoWidth;
        params.height = videoHeight;
        //setLayoutParams(params);
        requestLayout();
    }
}
