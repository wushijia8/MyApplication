package net.jaris.jaris_code.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.afinal.AfinalActivity;
import net.jaris.jaris_code.butterknife.ButterKnifeActivity;
import net.jaris.jaris_code.eventbus.EventBusActivity;
import net.jaris.jaris_code.fresco.FrescoActivity;
import net.jaris.jaris_code.glide.activity.GlideActivity;
import net.jaris.jaris_code.imageloader.ImageLoaderActivity;
import net.jaris.jaris_code.json.activity.FastJsonActivity;
import net.jaris.jaris_code.json.activity.GsonActivity;
import net.jaris.jaris_code.json.activity.NativeJsonPraseActivity;
import net.jaris.jaris_code.okHttp.activity.OkHttpActivity;
import net.jaris.jaris_code.okHttp.adapter.CommonFrameFragmentAdapter;
import net.jaris.jaris_code.okHttp.base.BaseFragment;
import net.jaris.jaris_code.picasso.activity.PicassoActivity;
import net.jaris.jaris_code.volley.VolleyActivity;
import net.jaris.jaris_code.xutils3.XUtils3MainActivity;

/**
 * Created by Jaris on 2016/8/31.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class CommonFrameFragment extends BaseFragment {

    private static final String TAG = CommonFrameFragment.class.getSimpleName();
    private TextView textView;
    private ListView mListView;

    private String[] datas;

    private CommonFrameFragmentAdapter  mCommonFrameFragmentAdapter;

    @Override
    protected View initView() {
        Log.e(TAG,"常用框架页面初始化....");
        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView  = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = datas[position];
                if(data.toLowerCase().equals("okhttp")){
                    Intent intent = new Intent(mContext,OkHttpActivity.class);
                    mContext.startActivity(intent);
                }else if (data.toLowerCase().equals("nativejsonprase")){
                    Intent intent = new Intent(mContext, NativeJsonPraseActivity.class);
                    startActivity(intent);
                }else if(data.toLowerCase().equals("gson")){
                    //点击条目跳转到Gson解析页面
                    Intent intent = new Intent(mContext,GsonActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("fastjson")){
                    Intent intent = new Intent(mContext, FastJsonActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("butterknife")){
                    Intent intent = new Intent(mContext, ButterKnifeActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("xutils3")){
                    Intent intent = new Intent(mContext, XUtils3MainActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("afinal")){
                    Intent intent = new Intent(mContext, AfinalActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("volley")){
                    Intent intent = new Intent(mContext, VolleyActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("eventbus")){
                    Intent intent = new Intent(mContext, EventBusActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("imageloader")){
                    Intent intent = new Intent(mContext, ImageLoaderActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("picasso")){
                    Intent intent = new Intent(mContext, PicassoActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("recyclerview")){
                    Toast.makeText(mContext, ""+data, Toast.LENGTH_SHORT).show();
                }else if(data.toLowerCase().equals("glide")){
                    Intent intent = new Intent(mContext, GlideActivity.class);
                    mContext.startActivity(intent);
                }else if(data.toLowerCase().equals("fresco")){
                    Intent intent = new Intent(mContext, FrescoActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG,"常用框架数据初始化....");
        //准备数据
        datas = new String[]{"OKHttp","NativeJsonPrase","Gson","FastJson","ButterKnife","xUtils3","Afinal","Volley"
                ,"EventBus","ImageLoader","Picasso","RecyclerView","Glide","Fresco"};
        //设置适配器
        mCommonFrameFragmentAdapter = new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(mCommonFrameFragmentAdapter);
    }
}
