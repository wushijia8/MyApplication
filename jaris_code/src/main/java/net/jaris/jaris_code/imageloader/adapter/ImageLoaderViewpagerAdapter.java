package net.jaris.jaris_code.imageloader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.Constants;

/**
 * Created by Jaris on 2016/11/8.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class ImageLoaderViewpagerAdapter extends PagerAdapter {

    private final ImageLoader imageLoader;
    private Context mContext;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.ic_launcher) //设置图片Uri为空或是错误时显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载或解码过程中发送错误显示的图片
            .resetViewBeforeLoading(true) //设置图片在下载前是否重置
            .cacheOnDisc(true)  //设置下载的图片是否缓存在SD卡中
            .imageScaleType(ImageScaleType.EXACTLY)     //设置图片以如何编码的方式显示
            .bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的编码类型
            .displayer(new FadeInBitmapDisplayer(300))  //设置图片渐变显示
            .build();

    public ImageLoaderViewpagerAdapter(Context context){
        mContext = context;

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public Object instantiateItem(View container, int position) {

        View view = View.inflate(mContext, R.layout.item_imageloader_viewpager,null);

        ImageView iv = (ImageView) view.findViewById(R.id.iv_imageloader_viewpager);

        imageLoader.displayImage(Constants.IMAGES[position],iv,options);

        ((ViewPager)container).addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return Constants.IMAGES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
