package net.jaris.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.utils.Utils;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Jaris on 2016/11/28.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class NetVideoPagerAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<MediaItem> mediaItems;

    public NetVideoPagerAdapter(Context context, ArrayList<MediaItem> mediaItems){
        this.context = context;
        this.mediaItems = mediaItems;
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mediaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_netvideo_pager,null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position得到数据
        MediaItem mediaItem = mediaItems.get(position);
        viewHolder.tvName.setText(mediaItem.getName());
        viewHolder.tvDesc.setText(mediaItem.getDesc());
        //使用xUtils请求图片
        //x.image().bind(viewHolder.ivIcon,mediaItem.getImageUrl());
        //使用Glide请求图片
        /*Glide.with(context).load(mediaItem.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.video_default)
                .error(R.drawable.video_default)
                .into(viewHolder.ivIcon);*/

        //使用Picasso请求图片
        Picasso.with(context).load(mediaItem.getImageUrl())
                .placeholder(R.drawable.video_default)
                .error(R.drawable.video_default)
                .into(viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;
    }

}
