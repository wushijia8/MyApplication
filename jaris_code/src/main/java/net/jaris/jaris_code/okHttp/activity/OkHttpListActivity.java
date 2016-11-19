package net.jaris.jaris_code.okHttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.okHttp.adapter.OkHttpListAdapter;
import net.jaris.jaris_code.okHttp.domain.DataBean;
import net.jaris.jaris_code.okHttp.utils.CacheUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Jaris on 2016/9/2.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class OkHttpListActivity extends Activity {

    private ListView llView;
    private ProgressBar progressBar;
    private TextView noData;
    private OkHttpListAdapter okHttpListAdapter;

    private String TAG = OkHttpListActivity.class.getSimpleName();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttplist);
        initView();
        getDataFromNet();
    }

    private void getDataFromNet() {
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

        //得到缓存的数据
        String saveJson = CacheUtils.getString(this,url);
        if(TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    private void initView() {
        noData = (TextView) findViewById(R.id.nodata);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        llView = (ListView) findViewById(R.id.llView);
    }


    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            noData.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            noData.setVisibility(View.GONE);

            switch (id)
            {
                case 100:
                    Toast.makeText(OkHttpListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkHttpListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }

            //解析显示数据
            if (response != null) {
                //缓存数据
                CacheUtils.putString(OkHttpListActivity.this,url,response);
                processData(response);
            }

        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    /**
     * 解析和显示数据
     * @param json
     */
    private void processData(String json) {

        //解析数据
        DataBean dataBean = parsedJson(json);
        List<DataBean.ItemData> datas = dataBean.getTrailers();
        if (datas != null && datas.size() > 0) {
            //有数据
            noData.setVisibility(View.GONE);
            //显示适配器
            okHttpListAdapter = new OkHttpListAdapter(OkHttpListActivity.this,datas);
            llView.setAdapter(okHttpListAdapter);
        }else {
            //没有数据
            noData.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 解析json数据
     *
     * @param response
     * @return
     */
    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.ItemData> trailers = new ArrayList<>();
                dataBean.setTrailers(trailers);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if (jsonObjectItem != null) {

                        DataBean.ItemData mediaItem = new DataBean.ItemData();

                        String movieName = jsonObjectItem.optString("movieName");//name
                        mediaItem.setMovieName(movieName);

                        String videoTitle = jsonObjectItem.optString("videoTitle");//desc
                        mediaItem.setVideoTitle(videoTitle);

                        String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                        mediaItem.setCoverImg(imageUrl);

                        String hightUrl = jsonObjectItem.optString("hightUrl");//data
                        mediaItem.setHightUrl(hightUrl);

                        //把数据添加到集合
                        trailers.add(mediaItem);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }

}
