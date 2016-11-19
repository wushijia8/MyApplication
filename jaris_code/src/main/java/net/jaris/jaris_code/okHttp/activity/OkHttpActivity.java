package net.jaris.jaris_code.okHttp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import net.jaris.jaris_code.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jaris on 2016/8/31.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class OkHttpActivity extends Activity implements View.OnClickListener {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final int GET = 1;
    private static final int POST = 2;
    private static final String TAG = OkHttpActivity.class.getSimpleName();

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123;

    private Button btnGetPost;
    private TextView tvResult;

    private Button btn_get_okhttputils;
    private Button btn_downloadfile;
    private ProgressBar mProgressBar;
    private ImageView iv_icon;
    private Button btn_image;
    private Button btn_imageList;

    private OkHttpClient client = new OkHttpClient();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
                case POST:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        btnGetPost = (Button) findViewById(R.id.btn_get_post);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btn_get_okhttputils = (Button) findViewById(R.id.btn_get_okhttputils);
        btn_downloadfile = (Button) findViewById(R.id.btn_downloadfile);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        btn_image = (Button) findViewById(R.id.btn_image);
        btn_imageList = (Button) findViewById(R.id.btn_imageList);

        btnGetPost.setOnClickListener(this);
        btn_get_okhttputils.setOnClickListener(this);
        btn_downloadfile.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_imageList.setOnClickListener(this);

    }

    private void getActionPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_post://使用原生的okhttp请求网络数据
                tvResult.setText("");
                //getDataFromGet();
                getDataFromPost();
                break;
            case R.id.btn_get_okhttputils:
                getDataGetByOkhttpUtils();
                getDataPostByOkhttpUtils();
                break;
            case R.id.btn_downloadfile:
                //判断当前系统版本
                if (Build.VERSION.SDK_INT >= 23) {
                    getActionPermission();
                }else {
                    downloadFile();
                }
                break;
            case R.id.btn_image:
                getImage();
                break;
            case R.id.btn_imageList:
                Intent intent = new Intent(OkHttpActivity.this,OkHttpListActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 使用POST请求网络数据
     */
    private void getDataFromPost() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api","");
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = POST;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 使用GET请求网络数据
     */
    private void getDataFromGet() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = getUrl("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * get请求
     * @param url：网络连接
     * @return
     * @throws IOException
     */
    private String getUrl(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * POST请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用OKHttpUtils的get请求网络文本数据
     */
    public void getDataGetByOkhttpUtils()
    {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public void getDataPostByOkhttpUtils()
    {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
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
            tvResult.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            tvResult.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(OkHttpActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkHttpActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //mProgressBar.setProgress((int) (100 * progress));
        }
    }

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile()
    {
        getActionPermission();
        String url = "http://vf2.mtime.cn/Video/2016/08/30/mp4/160830194403357293_480.mp4";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils-test.mp4")//
                {

                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


    /**
     * 用户确认权限后，回调该方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                downloadFile();
            } else {
                Toast.makeText(OkHttpActivity.this, "当前申请读取外部存储权限已被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 请求单张图片
     */
    public void getImage()
    {
        tvResult.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        tvResult.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("TAG", "onResponse：complete");
                        iv_icon.setImageBitmap(bitmap);
                    }
                });
    }


}
