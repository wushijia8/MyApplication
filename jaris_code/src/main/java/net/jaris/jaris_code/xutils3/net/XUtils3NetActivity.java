package net.jaris.jaris_code.xutils3.net;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_xutils3_net)
public class XUtils3NetActivity extends Activity {

    @ViewInject(R.id.tv_result)
    private TextView textView;

    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    final public static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_xutils3_net);
        x.view().inject(this);

        tvTitle.setText("xUtils3的网络模块");
    }

    @Event(value = {R.id.btn_get_post,R.id.btn_downloadfile,R.id.btn_uploadfile})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.btn_get_post:
                getAndPostNet();
                break;
            case R.id.btn_downloadfile:
                getActionPermission();
                break;
            case R.id.btn_uploadfile:
                uploadFile();
                break;
        }
    }

    private void uploadFile() {
        RequestParams params = new RequestParams("http://wushijia.oicp.net:8989/FileUpload/FileUploadServlet");
        //以表单的方式上传
        params.setMultipart(true);
        //设置上传文件的路径
        params.addBodyParameter("file",new File(Environment.getExternalStorageDirectory()+"/480.mp4"),null,"oppo.mp4");
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                //Toast.makeText(XUtils3NetActivity.this, "waiting...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStarted() {
                Toast.makeText(XUtils3NetActivity.this, "started...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressBar.setMax((int) total);
                progressBar.setProgress((int) current);
                //Toast.makeText(XUtils3NetActivity.this, "onLoading...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(XUtils3NetActivity.this, "success:"+result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(XUtils3NetActivity.this, "Error:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(XUtils3NetActivity.this, "cancelled"+cex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(XUtils3NetActivity.this, "finished...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadFile() {
        RequestParams params = new RequestParams("http://vf2.mtime.cn/Video/2016/10/31/mp4/161031112754888869_480.mp4");
        //设置下载的保存路径
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/480.mp4");
        //设置是否可以立即取消下载
        params.setCancelFast(true);
        //设置是否可以自动命名
        params.setAutoRename(false);
        //设置断点续传
        params.setAutoResume(true);
        //自定义线程池，有效值范围在[1,3]，设置为3时，可能阻塞图片加载
        params.setExecutor(new PriorityExecutor(3,true));
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                //Toast.makeText(XUtils3NetActivity.this, "waiting...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStarted() {
                Toast.makeText(XUtils3NetActivity.this, "started...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressBar.setMax((int) total);
                progressBar.setProgress((int) current);
                //Toast.makeText(XUtils3NetActivity.this, "onLoading...", Toast.LENGTH_SHORT).show();
            }


            /**
             * 当下载成功时回调该方法，并且将下载的路径回传过来
             * @param result
             */
            @Override
            public void onSuccess(File result) {
                Toast.makeText(XUtils3NetActivity.this, "success:"+result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(XUtils3NetActivity.this, "Error:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(XUtils3NetActivity.this, "cancelled"+cex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(XUtils3NetActivity.this, "finished...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getActionPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
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
                Toast.makeText(XUtils3NetActivity.this, "当前申请读取外部存储权限已被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAndPostNet() {
        RequestParams params = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        //1.Get请求
        //x.http().get(params, new Callback.CommonCallback<String>() {
        //Post请求
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(XUtils3NetActivity.this, "xUtils3联网请求成功=="+result, Toast.LENGTH_SHORT).show();
                textView.setText("Get请求的结果："+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(XUtils3NetActivity.this, "xUtils3联网请求失败=="+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(XUtils3NetActivity.this, "onCancelled=="+cex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(XUtils3NetActivity.this, "onFinished", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
