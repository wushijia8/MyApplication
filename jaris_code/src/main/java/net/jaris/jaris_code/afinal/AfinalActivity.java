package net.jaris.jaris_code.afinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.xutils3.net.XUtils3NetActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;

public class AfinalActivity extends FinalActivity {

    @ViewInject(id=R.id.bt_afinal_loadimage,click = "bt_afinal_loadimage_click")
    private Button btAfinalLoadImage;

    @ViewInject(id=R.id.bt_afinal_gettext,click = "bt_afinal_gettext_click")
    private Button btAfinalGetText;

    @ViewInject(id=R.id.bt_afinal_loadfile,click = "bt_afinal_loadfile_click")
    private Button btAfinalLoadFile;

    @ViewInject(id=R.id.bt_afinal_uploadtext,click = "bt_afinal_uploadtext_click")
    private Button btAfinalUploadText;

    @ViewInject(id=R.id.iv_afinal)
    private ImageView ivAfinal;

    @ViewInject(id=R.id.tv_afinal_result)
    private TextView tvAfinalResult;

    @ViewInject(id=R.id.tv_title)
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal);

        initView();
    }

    private void initView() {
        tvTitle.setText("Afinal");
    }

    public void bt_afinal_loadimage_click(View v){
        FinalBitmap finalBitmap = FinalBitmap.create(this);

        //网络请求图片时默认显示的图片
        finalBitmap.configLoadingImage(R.mipmap.ic_launcher);

        //开始加载图片
        finalBitmap.display(ivAfinal,"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");

    }

    public void bt_afinal_gettext_click(View v){
        FinalHttp finalHttp = new FinalHttp();
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        finalHttp.get(url, new AjaxCallBack<Object>() {
            @Override
            public void onStart() {
                tvAfinalResult.setText("开始加载");
                super.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                //显示加载成功后的数据
                tvAfinalResult.setText(o.toString());
                super.onSuccess(o);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                tvAfinalResult.setText("加载失败");
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    public void bt_afinal_loadfile_click(View v){
        getActionPermission();
    }

    private void getActionPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
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
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                downloadFile();
            } else {
                Toast.makeText(AfinalActivity.this, "当前申请读取外部存储权限已被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadFile() {
        FinalHttp finalHttp = new FinalHttp();
        String url = "http://vfx.mtime.cn/Video/2016/11/02/mp4/161102083059175649_480.mp4";
        //存放视频文件到本地位置
        String target = Environment.getExternalStorageDirectory()+"/afinalmovie.mp4";
        finalHttp.download(url, target, new AjaxCallBack<File>() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                tvAfinalResult.setText("下载文件失败");
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(File file) {
                tvAfinalResult.setText("下载文件成功");
                super.onSuccess(file);
            }

            @Override
            public void onStart() {
                tvAfinalResult.setText("开始下载");
                super.onStart();
            }
        });
    }

    public void bt_afinal_uploadtext_click(View v){
        FinalHttp finalHttp = new FinalHttp();
        String url = "http://wushijia.oicp.net:8989/FileUpload/FileUploadServlet";
        AjaxParams params = new AjaxParams();
        //获取要上传的本地资源
        try {
            params.put("File",new File(Environment.getExternalStorageDirectory()+"/afinalmovie.mp4"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finalHttp.post(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onStart() {
                tvAfinalResult.setText("开始上传");
                super.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                tvAfinalResult.setText("上传成功");
                super.onSuccess(o);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                tvAfinalResult.setText("上传失败");
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }



}
