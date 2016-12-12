package net.jaris.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.utils.Utils;
import net.jaris.mobileplayer.video.Video;

import java.util.ArrayList;

/**
 * Created by Jaris on 2016/11/28.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class VideoPagerAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<MediaItem> mediaItems;
    private Utils utils;
    private boolean isVideo;

    public VideoPagerAdapter(Context context, ArrayList<MediaItem> mediaItems,boolean isVideo){
        this.context = context;
        this.mediaItems = mediaItems;
        this.isVideo = isVideo;
        utils = new Utils();
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
            convertView = View.inflate(context, R.layout.item_video_pager,null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvSize = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position得到数据
        MediaItem mediaItem = mediaItems.get(position);
        viewHolder.tvName.setText(mediaItem.getName());
        viewHolder.tvSize.setText(Formatter.formatFileSize(context,mediaItem.getSize()));
        viewHolder.tvTime.setText(utils.stringForTime((int) mediaItem.getDuration()));

        if(!isVideo){
            //音频
            viewHolder.ivIcon.setImageResource(R.drawable.music_default_bg);

        }

        return convertView;
    }

    static class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvTime;
        TextView tvSize;
    }

}
