package net.jaris.jaris_code.okHttp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jaris on 2016/8/31.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 *
 * 常用框架的Fragment
 *
 */
public class CommonFrameFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] mDatas;

    public CommonFrameFragmentAdapter(Context context,String[] datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setPadding(10,10,0,10);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setText(mDatas[position]);
        return textView;
    }
}
