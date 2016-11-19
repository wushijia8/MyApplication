package net.jaris.jaris_code.eventbus;

import android.app.Activity;
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

public class EventBusSendActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_eventbus_send_main)
    Button btEventbusSendMain;
    @Bind(R.id.bt_eventbus_send_sticky)
    Button btEventbusSendSticky;
    @Bind(R.id.tv_eventbus_send_result)
    TextView tvEventbusSendResult;

    boolean isFirstFlag =  true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    private void initListener() {

        //接收粘性数据
        btEventbusSendSticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFirstFlag){
                    //4.注册
                    EventBus.getDefault().register(EventBusSendActivity.this);
                    isFirstFlag = false;
                }
            }
        });
    }

    //3.接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sticktEventBus(StickyEvent event){
        //显示接收的数据
        tvEventbusSendResult.setText(event.msg);
    }

    private void initData() {
        tvTitle.setText("EventBus发送数据页面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5.解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.this);
    }
}
