package net.jaris.jaris_code.json.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.json.bean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GsonActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_gson_tojavaobject)
    Button btnGsonTojavaobject;
    @Bind(R.id.btn_gosn_tojavalist)
    Button btnGosnTojavalist;
    @Bind(R.id.btn_gson_javatojsonobject)
    Button btnGsonJavatojsonobject;
    @Bind(R.id.btn_gson_javatojsonarray)
    Button btnGsonJavatojsonarray;
    @Bind(R.id.tv_gson_orignal)
    TextView tvGsonOrignal;
    @Bind(R.id.tv_gson_last)
    TextView tvGsonLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        ButterKnife.bind(this);

        tvTitle.setText("GSON解析");
        initListener();
    }

    private void initListener() {
        btnGsonTojavaobject.setOnClickListener(this);
        btnGosnTojavalist.setOnClickListener(this);
        btnGsonJavatojsonobject.setOnClickListener(this);
        btnGsonJavatojsonarray.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gson_tojavaobject:
                jsonToJavaObjectByGson();
                break;
            case R.id.btn_gosn_tojavalist:
                jsonToJavaListByGson();
                break;
            case R.id.btn_gson_javatojsonobject:
                javaToJsonObjectByGson();
                break;
            case R.id.btn_gson_javatojsonarray:
                javaToJsonArrayByGson();
                break;
        }
    }

    private void javaToJsonArrayByGson() {
        //获取或创建java对象
        List<ShopInfo>  shops = new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(1, "龙虾", 250.0, "longxia");
        //生成JSON数据
        shops.add(baoyu);
        shops.add(longxia);
        //展示JSON数据
        Gson gson = new Gson();
        String json = gson.toJson(shops);
        tvGsonOrignal.setText(shops.toString());
        tvGsonLast.setText(json);
    }

    private void javaToJsonObjectByGson() {
        //获取或创建java对象
        ShopInfo shopInfo = new ShopInfo(1,"鲍鱼",250.0,"baoyu");
        //生成Json数据
        Gson gson = new Gson();
        //显示数据
        String json = gson.toJson(shopInfo);
        tvGsonOrignal.setText(shopInfo.toString());
        tvGsonLast.setText(json);
    }

    private void jsonToJavaListByGson() {
        String json = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f1.jpg\",\n" +
                "        \"name\": \"大虾1\",\n" +
                "        \"price\": 12.3\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f2.jpg\",\n" +
                "        \"name\": \"大虾2\",\n" +
                "        \"price\": 12.5\n" +
                "    }\n" +
                "]";
        Gson gson = new Gson();
        List<ShopInfo> shops = gson.fromJson(json,new TypeToken<List<ShopInfo>>(){}.getType());
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(shops.toString());
    }

    //将json格式的字符串转换为java对象
    private void jsonToJavaObjectByGson() {
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        // 2 解析JSON数据
        Gson gson = new Gson();

        ShopInfo shopInfo = gson.fromJson(json, ShopInfo.class);

        // 3 展示数据
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(shopInfo.toString());

    }
}
