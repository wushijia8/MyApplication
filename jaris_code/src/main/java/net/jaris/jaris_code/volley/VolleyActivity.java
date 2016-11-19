package net.jaris.jaris_code.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.jaris.jaris_code.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VolleyActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_volley_get)
    Button btVolleyGet;
    @Bind(R.id.bt_volley_post)
    Button btVolleyPost;
    @Bind(R.id.bt_volley_getjson)
    Button btVolleyGetjson;
    @Bind(R.id.bt_volley_imagerequest)
    Button btVolleyImagerequest;
    @Bind(R.id.bt_volley_imageloader)
    Button btVolleyImageloader;
    @Bind(R.id.bt_volley_networkimageview)
    Button btVolleyNetworkimageview;
    @Bind(R.id.iv_volley_result)
    ImageView ivVolleyResult;
    @Bind(R.id.iv_volley_networkimageview)
    NetworkImageView ivVolleyNetworkimageview;
    @Bind(R.id.tv_volley_result)
    TextView tvVolleyResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        ButterKnife.bind(this);

        initData();

        initListener();
    }

    private void initListener() {
        //get请求
        btVolleyGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个请求队列
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                //创建一个请求
                String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    //正确接收数据时回调该方法
                    @Override
                    public void onResponse(String s) {
                        tvVolleyResult.setText(s);
                    }
                }, new Response.ErrorListener() {

                    //发生错误时回调该方法
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tvVolleyResult.setText("加载错误");
                    }
                });
                //将创建的请求添加到请求队列中
                requestQueue.add(stringRequest);
            }
        });

        //post请求
        btVolleyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        tvVolleyResult.setText(s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tvVolleyResult.setText("请求失败"+volleyError);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();
                        //map.put("value1","param1");
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        //获取json数据
        btVolleyGetjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvVolleyResult.setText(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tvVolleyResult.setText("请求失败："+volleyError);
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

        //imageRequest加载图片
        btVolleyImagerequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个请求队列
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                //创建一个图片请求
                String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivVolleyResult.setVisibility(View.VISIBLE);
                        ivVolleyResult.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tvVolleyResult.setText(volleyError.getMessage());
                        ivVolleyResult.setVisibility(View.VISIBLE);
                        ivVolleyResult.setImageResource(R.mipmap.ic_launcher);
                    }
                });
                //将请求添加到请求队列中
                requestQueue.add(imageRequest);
            }
        });

        //imageLoader加载图片
        btVolleyImageloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                /*ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String s) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String s, Bitmap bitmap) {

                    }
                });*/
                ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());
                String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
                ivVolleyResult.setVisibility(View.VISIBLE);
                ImageLoader.ImageListener imageListener = imageLoader.getImageListener(ivVolleyResult,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                imageLoader.get(url,imageListener);
            }
        });

        //加载图片
        btVolleyNetworkimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivVolleyNetworkimageview.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
                //设置默认图片
                ivVolleyNetworkimageview.setDefaultImageResId(R.mipmap.ic_launcher);
                //设置异常图片
                ivVolleyNetworkimageview.setErrorImageResId(R.mipmap.ic_launcher);
                //加载网络图片
                String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
                ivVolleyNetworkimageview.setImageUrl(url,imageLoader);
            }
        });
    }

    private void initData() {
        tvTitle.setText("Volley");
    }

}
