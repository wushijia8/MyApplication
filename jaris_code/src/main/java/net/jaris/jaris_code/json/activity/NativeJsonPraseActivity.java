package net.jaris.jaris_code.json.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.json.bean.DataInfo;
import net.jaris.jaris_code.json.bean.FilmInfo;
import net.jaris.jaris_code.json.bean.MoveInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NativeJsonPraseActivity extends Activity implements View.OnClickListener {

    @butterknife.Bind(R.id.tv_title)
    TextView tvTitle;
    @butterknife.Bind(R.id.btn_native_tojavaobject)
    Button btnNativeTojavaobject;
    @butterknife.Bind(R.id.btn_native_tojavalist)
    Button btnNativeTojavalist;
    @butterknife.Bind(R.id.btn_native_complex)
    Button btnNativeComplex;
    @butterknife.Bind(R.id.btn_native_special)
    Button btnNativeSpecial;
    @butterknife.Bind(R.id.tv_native_orignal)
    TextView tvNativeOrignal;
    @butterknife.Bind(R.id.tv_native_last)
    TextView tvNativeLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_json_prase);
        ButterKnife.bind(this);

        initView();

        initListener();
    }

    private void initListener() {
        btnNativeTojavaobject.setOnClickListener(this);
        btnNativeTojavalist.setOnClickListener(this);
        btnNativeComplex.setOnClickListener(this);
        btnNativeSpecial.setOnClickListener(this);
    }

    private void initView() {
        tvTitle.setText("手动JSON解析");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_native_tojavaobject:
                jsonToJavaObjectByNativer();
                break;
            case R.id.btn_native_tojavalist:
                jsonToJavaListByNative();
                break;
            case R.id.btn_native_complex:
                jsonToJavaOfComplex();
                break;
            case R.id.btn_native_special:
                jsonToJavaOfSpecial();
                break;
        }
    }

    //特殊json数据解析
    private void jsonToJavaOfSpecial() {
        Toast.makeText(this, "特殊json数据解析", Toast.LENGTH_SHORT).show();
        String json = "{\n" +
                "    \"code\": 0,\n" +
                "    \"list\": {\n" +
                "        \"0\": {\n" +
                "            \"aid\": \"6008965\",\n" +
                "            \"author\": \"哔哩哔哩番剧\",\n" +
                "            \"coins\": 170,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 21:34\",\n" +
                "            \"credit\": 0,\n" +
                "            \"description\": \"#08 秘密房间\",\n" +
                "            \"duration\": \"24:18\",\n" +
                "            \"favorites\": 83,\n" +
                "            \"mid\": 928123,\n" +
                "            \"online\": 2604,\n" +
                "            \"pic\": \"http://i2.hdslb.com/bfs/archive/cce23620423efa315ae8a818d70a269779ed132f.jpg\",\n" +
                "            \"play\": \"42699\",\n" +
                "            \"review\": 6,\n" +
                "            \"subtitle\": \"\",\n" +
                "            \"title\": \"【7月】这个美术社大有问题! 08\",\n" +
                "            \"typeid\": 33,\n" +
                "            \"typename\": \"连载动画\",\n" +
                "            \"video_review\": \"3505\"\n" +
                "        },\n" +
                "        \"1\": {\n" +
                "            \"aid\": \"6008938\",\n" +
                "            \"author\": \"哔哩哔哩番剧\",\n" +
                "            \"coins\": 404,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 21:33\",\n" +
                "            \"credit\": 0,\n" +
                "            \"description\": \"#07 希望之峰学园史上最大最恶的事件\",\n" +
                "            \"duration\": \"23:40\",\n" +
                "            \"favorites\": 236,\n" +
                "            \"mid\": 928123,\n" +
                "            \"online\": 2239,\n" +
                "            \"pic\": \"http://i0.hdslb.com/bfs/archive/caebe1e89ad2c1e7290ff04d3aa00fbac58249c1.jpg\",\n" +
                "            \"play\": \"86080\",\n" +
                "            \"review\": 46,\n" +
                "            \"subtitle\": \"\",\n" +
                "            \"title\": \"【7月】弹丸论破3 The End of 希望峰学园 -绝望篇- 07\",\n" +
                "            \"typeid\": 33,\n" +
                "            \"typename\": \"连载动画\",\n" +
                "            \"video_review\": \"5851\"\n" +
                "        },\n" +
                "        \"2\": {\n" +
                "            \"aid\": \"6004817\",\n" +
                "            \"author\": \"TV-TOKYO\",\n" +
                "            \"coins\": 992,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 17:45\",\n" +
                "            \"credit\": 0,\n" +
                "            \"description\": \"#693 写轮眼再现\",\n" +
                "            \"duration\": \"23:13\",\n" +
                "            \"favorites\": 277,\n" +
                "            \"mid\": 21453565,\n" +
                "            \"online\": 942,\n" +
                "            \"pic\": \"http://i2.hdslb.com/bfs/archive/d051fce7dd58a6a4df1f90fc8badbea59da45dc4.jpg\",\n" +
                "            \"play\": \"326043\",\n" +
                "            \"review\": 11,\n" +
                "            \"subtitle\": \"\",\n" +
                "            \"title\": \"【1月】火影忍者 疾风传 693\",\n" +
                "            \"typeid\": 33,\n" +
                "            \"typename\": \"连载动画\",\n" +
                "            \"video_review\": \"18651\"\n" +
                "        },\n" +
                "        \"3\": {\n" +
                "            \"aid\": \"6008970\",\n" +
                "            \"author\": \"TV-TOKYO\",\n" +
                "            \"coins\": 13,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 21:35\",\n" +
                "            \"credit\": 0,\n" +
                "            \"description\": \"#40 心灵感应消音器\",\n" +
                "            \"duration\": \"4:18\",\n" +
                "            \"favorites\": 4,\n" +
                "            \"mid\": 21453565,\n" +
                "            \"online\": 599,\n" +
                "            \"pic\": \"http://i2.hdslb.com/bfs/archive/5f1f7718f82fc5f9de512e94c8b99039a0d654a9.jpg\",\n" +
                "            \"play\": \"4084\",\n" +
                "            \"review\": 3,\n" +
                "            \"subtitle\": \"\",\n" +
                "            \"title\": \"【7月】齐木楠雄的灾难(日播版) 40\",\n" +
                "            \"typeid\": 33,\n" +
                "            \"typename\": \"连载动画\",\n" +
                "            \"video_review\": \"514\"\n" +
                "        },\n" +
                "        \"4\": {\n" +
                "            \"aid\": \"5992302\",\n" +
                "            \"author\": \"TV-TOKYO\",\n" +
                "            \"coins\": 816,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-24 21:52\",\n" +
                "            \"credit\": 0,\n" +
                "            \"description\": \"#39 毫无疑心！齐木久留美\",\n" +
                "            \"duration\": \"4:18\",\n" +
                "            \"favorites\": 247,\n" +
                "            \"mid\": 21453565,\n" +
                "            \"online\": 54,\n" +
                "            \"pic\": \"http://i1.hdslb.com/bfs/archive/ccf3759a471108099cbf1c761857a575dfe028a8.jpg\",\n" +
                "            \"play\": \"528275\",\n" +
                "            \"review\": 32,\n" +
                "            \"subtitle\": \"\",\n" +
                "            \"title\": \"【7月】齐木楠雄的灾难(日播版) 39\",\n" +
                "            \"typeid\": 33,\n" +
                "            \"typename\": \"连载动画\",\n" +
                "            \"video_review\": \"4069\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        //创建封装的Java对象
        FilmInfo filmInfo = new FilmInfo();

        try {
            JSONObject jsonObject = new JSONObject(json);
            //第一层解析
            int code = jsonObject.optInt("code");
            JSONObject list = jsonObject.optJSONObject("list");

            //第一层封装
            filmInfo.setCode(code);
            List<FilmInfo.FilmBean> lists = new ArrayList<>();
            filmInfo.setList(lists);
            //第二层解析
            for (int i = 0;i<list.length();i++){
                JSONObject jsonObject1 = list.optJSONObject(i + "");
                if (jsonObject1 != null) {
                    String aid = jsonObject1.optString("aid");
                    String author = jsonObject1.optString("author");
                    int coins = jsonObject1.optInt("coins");
                    String copyright = jsonObject1.optString("copyright");
                    String create = jsonObject1.optString("create");
                    //第二次数据封装
                    FilmInfo.FilmBean filmBean = new FilmInfo.FilmBean();;
                    filmBean.setAid(aid);
                    filmBean.setAuthor(author);
                    filmBean.setCoins(coins);
                    filmBean.setCopyright(copyright);
                    filmBean.setCreate(create);
                    lists.add(filmBean);
                }
            }
        } catch (JSONException e) {


        }
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(filmInfo.toString());
    }

    //复杂json数据解析
    private void jsonToJavaOfComplex() {
        String json = "{\"rs_code\":\"1000\",\"data\":{\"count\":5,\"items\":[{\"id\":45,\"title\":\"坚果\"},{\"id\":132,\"title\":\"炒货\"},{\"id\":166,\"title\":\"蜂蜜\"},\n" +
                "{\"id\":195,\"title\":\"果冻\"},{\"id\":196,\"title\":\"巧克力\"}]},\"rs_msg\":\"success\"}";

        DataInfo dataInfo = new DataInfo();

        try {
            JSONObject jsonObject = new JSONObject(json);
            //第一层解析
            JSONObject data = jsonObject.optJSONObject("data");
            String rs_code = jsonObject.optString("rs_code");
            String rs_msg = jsonObject.optString("rs_msg");

            //第一层封装
            dataInfo.setRs_code(rs_code);
            dataInfo.setRs_msg(rs_msg);
            DataInfo.DataBean dataBean = new DataInfo.DataBean();
            dataInfo.setData(dataBean);

            //第二层解析
            int count = data.optInt("count");
            JSONArray items = data.optJSONArray("items");

            //第二层数据的封装
            dataBean.setCount(count);
            List<DataInfo.DataBean.ItemsBean> itemsBean = new ArrayList<>();
            dataBean.setItems(itemsBean);

            //第三层解析
            for (int i = 0;i<items.length();i++){
                JSONObject jsonObject1 = items.optJSONObject(i);
                if (jsonObject1 != null) {
                    int id = jsonObject1.optInt("id");
                    String title = jsonObject1.optString("title");

                    //第三层数据的封装
                    DataInfo.DataBean.ItemsBean bean = new DataInfo.DataBean.ItemsBean();
                    bean.setId(id);
                    bean.setTitle(title);
                    itemsBean.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //显示JSON数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(dataInfo.toString());
    }

    private void jsonToJavaListByNative() {
        String json = "[{\"id\":63013,\"movieName\":" +
                "\"《降临》终极版预告片\"," +
                "\"summary\":\"双11北美对垒李安新作\"},"+
                "{\"id\":63013,\"movieName\":" +
                "\"《降临》终极版预告片\"," +
                "\"summary\":\"双11北美对垒李安新作\"}]";
        List<MoveInfo> moveInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    int id = jsonObject.optInt("id");
                    String movieName = jsonObject.optString("movieName");
                    String summary = jsonObject.optString("summary");
                    MoveInfo moveInfo = new MoveInfo(id,movieName,summary);
                    moveInfos.add(moveInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvNativeOrignal.setText(json);
        tvNativeLast.setText(moveInfos.toString());
    }

    //将json格式的字符串转换成java对象
    private void jsonToJavaObjectByNativer() {
        //获取或创建json数据
        String json = "[{\"id\":63013,\"movieName\":" +
                "\"《降临》终极版预告片\"," +
                "\"summary\":\"双11北美对垒李安新作\"}]";

        MoveInfo moveInfo = null;
        //解析json数据
        try {
            JSONObject jsonObject = new JSONObject(json);
            int id = jsonObject.optInt("id");
            String movieName = jsonObject.optString("movieName");
            String summary = jsonObject.optString("summary");
            moveInfo = new MoveInfo(id,movieName,summary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(moveInfo.toString());
    }
}
