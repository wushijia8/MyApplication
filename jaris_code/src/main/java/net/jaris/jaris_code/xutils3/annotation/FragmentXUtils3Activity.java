package net.jaris.jaris_code.xutils3.annotation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import net.jaris.jaris_code.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_xutils3_fragment)
public class FragmentXUtils3Activity extends android.support.v4.app.FragmentActivity {

    @ViewInject(R.id.tv_title)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_xutils3_fragment);
        x.view().inject(this);
        textView.setText("在Fragment中使用注解");

        //1.得到FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //3.替换Fragment
        ft.replace(R.id.fl_content,new DemoFragment());
        //4.提交
        ft.commit();
    }
}
