package net.jaris.jaris_code.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import net.jaris.jaris_code.okHttp.base.BaseFragment;

/**
 * Created by Jaris on 2016/8/31.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class OtherFragment extends BaseFragment {
    
    private static final String TAG = OtherFragment.class.getSimpleName();
    private TextView textView;
    
    @Override
    protected View initView() {
        Log.e(TAG,"其他页面初始化....");
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG,"其他数据初始化....");
        textView.setText("其他页面");
    }
}
