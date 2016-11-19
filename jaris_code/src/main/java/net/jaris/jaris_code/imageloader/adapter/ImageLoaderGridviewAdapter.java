package net.jaris.jaris_code.imageloader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jaris on 2016/11/8.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class ImageLoaderGridviewAdapter extends BaseAdapter {

    private final ImageLoader imageLoader;
    private Context mContext;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher)    //设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher) //设置图片Uri为空或是错误时显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载或解码过程中发送错误显示的图片
            .cacheInMemory(true)    //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)  //设置下载的图片是否缓存在SD卡中
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public ImageLoaderGridviewAdapter(Context context) {
        mContext = context;

        //初始化ImageLoader
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return Constants.IMAGES.length;
    }

    @Override
    public Object getItem(int position) {
        return Constants.IMAGES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_imageloader_gridview,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //显示图片
        imageLoader.displayImage(Constants.IMAGES[position],viewHolder.iv,options);

        return convertView;
    }

    class ViewHolder{

        @Bind(R.id.iv_imageloader_gridview)
        ImageView iv;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
