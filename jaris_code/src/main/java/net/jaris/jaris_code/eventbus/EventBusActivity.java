package net.jaris.jaris_code.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.eventbus.event.MessageEvent;
import net.jaris.jaris_code.eventbus.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventBusActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_eventbus_send)
    Button btEventbusSend;
    @Bind(R.id.bt_eventbus_sticky)
    Button btEventbusSticky;
    @Bind(R.id.tv_eventbus_result)
    TextView tvEventbusResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);

        initData();

        initListener();
    }

    private void initListener() {
        btEventbusSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventBusActivity.this,EventBusSendActivity.class);
                startActivity(intent);
            }
        });

        btEventbusSticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2.发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                //跳转到发送数据的页面
                Intent intent = new Intent(EventBusActivity.this,EventBusSendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        tvTitle.setText("EventBus");

        //1.注册广播
        EventBus.getDefault().register(EventBusActivity.this);
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        //显示接收的消息
        tvEventbusResult.setText(event.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2.解注册
        EventBus.getDefault().unregister(EventBusActivity.this);
    }
}
