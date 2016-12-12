package net.jaris.mobileplayer.netvideo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Cache;

import net.jaris.mobileplayer.R;
import net.jaris.mobileplayer.adapter.NetVideoPagerAdapter;
import net.jaris.mobileplayer.domain.MediaItem;
import net.jaris.mobileplayer.utils.CacheUtils;
import net.jaris.mobileplayer.utils.Constants;
import net.jaris.mobileplayer.video.SystemVideoPlayer;
import net.jaris.mobileplayer.video.Video;
import net.jaris.mobileplayer.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NetVideoActivity extends Activity {


    @Bind(R.id.listview)
    XListView mListView;
    @Bind(R.id.tv_nonet)
    TextView mTvNoNet;
    @Bind(R.id.pb_loading)
    ProgressBar mProgressBar;

    /**
     * 数据集合
     */
    private ArrayList<MediaItem> mediaItems;

    private NetVideoPagerAdapter netVideoPagerAdapter;

    private boolean isLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_video);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        mListView.setOnItemClickListener(new MyOnItemClickListener());
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(new MyIXListViewListener());
    }

    class MyIXListViewListener implements XListView.IXListViewListener{

        @Override
        public void onRefresh() {
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            getMoreDataFromNet();
        }
    }

    private void getMoreDataFromNet() {
        RequestParams params = new RequestParams(Constants.NET_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功：" + result);
                isLoadMore = true;
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网失败：" + ex.getMessage());
                isLoadMore = false;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("联网取消：" + cex.getMessage());
                isLoadMore = false;
            }

            @Override
            public void onFinished() {
                LogUtil.e("完成");
                isLoadMore = false;
            }
        });
    }

    private void initData() {
        String saveJson = CacheUtils.getString(NetVideoActivity.this,Constants.NET_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NET_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功：" + result);

                //缓存数据
                CacheUtils.putString(NetVideoActivity.this,Constants.NET_URL,result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网失败：" + ex.getMessage());
                showData();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("联网取消：" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("完成");
            }
        });
    }

    private void processData(String json) {

        if(!isLoadMore){
            mediaItems = parseJson(json);
            showData();
        }else {
            //加载更多
            isLoadMore = false;
            mediaItems.addAll(parseJson(json));
            //刷新适配器
            netVideoPagerAdapter.notifyDataSetChanged();
            onLoad();
        }
    }

    private void showData() {
        //设置适配器
        if (mediaItems != null && mediaItems.size() > 0) {
            netVideoPagerAdapter = new NetVideoPagerAdapter(NetVideoActivity.this, mediaItems);
            mListView.setAdapter(netVideoPagerAdapter);
            onLoad();
            mTvNoNet.setVisibility(View.GONE);
        } else {
            mTvNoNet.setVisibility(View.VISIBLE);
        }

        mProgressBar.setVisibility(View.GONE);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("更新时间："+getSystemTime());
    }


    @TargetApi(Build.VERSION_CODES.N)
    public String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 解析json数据
     * 1.用系统接口解析json数据
     * 2.使用第三方工具
     *
     * @param json
     * @return
     */
    private ArrayList<MediaItem> parseJson(String json) {
        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    if (jsonObjectItem != null) {
                        MediaItem mediaItem = new MediaItem();
                        String moiveName = jsonObjectItem.optString("movieName");
                        String videoTitle = jsonObjectItem.optString("videoTitle");
                        String imageUrl = jsonObjectItem.optString("coverImg");
                        String hightUrl = jsonObjectItem.optString("hightUrl");
                        mediaItem.setName(moiveName);
                        mediaItem.setData(videoTitle);
                        mediaItem.setImageUrl(imageUrl);
                        mediaItem.setData(hightUrl);

                        mediaItems.add(mediaItem);//添加到集合中

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mediaItems;
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(NetVideoActivity.this,SystemVideoPlayer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("videolist",mediaItems);
            intent.putExtras(bundle);
            intent.putExtra("position",position-1);
            startActivity(intent);
        }
    }
}
