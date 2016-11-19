package net.jaris.jaris_code.json.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.json.bean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FastJsonActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_fastjson_tojavaobject)
    Button btnFastjsonTojavaobject;
    @Bind(R.id.btn_fastjson_tojavalist)
    Button btnFastjsonTojavalist;
    @Bind(R.id.btn_fastjson_javatojsonobject)
    Button btnFastjsonJavatojsonobject;
    @Bind(R.id.btn_fastjson_javatojsonarray)
    Button btnFastjsonJavatojsonarray;
    @Bind(R.id.tv_fastjson_orignal)
    TextView tvFastjsonOrignal;
    @Bind(R.id.tv_fastjson_last)
    TextView tvFastjsonLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_json);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        btnFastjsonTojavaobject.setOnClickListener(this);
        btnFastjsonTojavalist.setOnClickListener(this);
        btnFastjsonJavatojsonobject.setOnClickListener(this);
        btnFastjsonJavatojsonarray.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fastjson_tojavaobject:
                jsonToJavaObjectByFastJson();
                break;
            case R.id.btn_fastjson_tojavalist:
                jsonToJavaListByFastJson();
                break;
            case R.id.btn_fastjson_javatojsonobject:
                javaToJsonObjectByFastJson();
                break;
            case R.id.btn_fastjson_javatojsonarray:
                javaToJsonArrayByFastJson();
                break;
        }
    }

    //将java对象的集合转换为JSON字符串的数组
    private void javaToJsonArrayByFastJson() {
        List<ShopInfo> shops = new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 250.1, "longxia");
        shops.add(baoyu);
        shops.add(longxia);
        String json = JSON.toJSONString(shops);
        tvFastjsonOrignal.setText(shops.toString());
        tvFastjsonLast.setText(json);
    }

    //将Java对象转换为JSON字符串
    private void javaToJsonObjectByFastJson() {
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        String json = JSON.toJSONString(shopInfo);
        tvFastjsonOrignal.setText(shopInfo.toString());
        tvFastjsonLast.setText(json);
    }

    private void jsonToJavaListByFastJson() {
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
        List<ShopInfo> shopInfos = JSON.parseArray(json, ShopInfo.class);
        tvFastjsonOrignal.setText(json);
        tvFastjsonLast.setText(shopInfos.toString());
    }

    //将json对象转换为java对象
    private void jsonToJavaObjectByFastJson() {
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";
        ShopInfo shopInfo = JSON.parseObject(json, ShopInfo.class);
        tvFastjsonOrignal.setText(json);
        tvFastjsonLast.setText(shopInfo.toString());
    }


}
