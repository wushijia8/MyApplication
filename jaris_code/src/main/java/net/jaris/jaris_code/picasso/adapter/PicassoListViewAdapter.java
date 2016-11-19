package net.jaris.jaris_code.picasso.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.imageloader.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jaris on 2016/11/9.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class PicassoListViewAdapter extends BaseAdapter {


    private Context mContext;

    public PicassoListViewAdapter(Context context){
        mContext = context;
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picasso_listview,null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText("item"+(position+1));

        //加载图片
        Picasso.with(mContext)
                .load(Constants.IMAGES[position])
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.iv);

        return convertView;
    }

    class ViewHolder{

        @Bind(R.id.iv_picasso_item)
        ImageView iv;

        @Bind(R.id.tv_picasso_name)
        TextView name;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
