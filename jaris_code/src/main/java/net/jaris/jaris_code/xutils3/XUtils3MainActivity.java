package net.jaris.jaris_code.xutils3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.xutils3.annotation.FragmentXUtils3Activity;
import net.jaris.jaris_code.xutils3.net.XUtils3NetActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_xutils3_main)
public class XUtils3MainActivity extends Activity {

    @ViewInject(R.id.tv_title)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        textView.setText("xUtils3的使用");
    }

    @Event(value = {R.id.btn_annotation,R.id.btn_net,R.id.btn_image,R.id.btn_image_list})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.btn_annotation:
                Intent intent = new Intent(this, FragmentXUtils3Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_net:
                intent = new Intent(this, XUtils3NetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_image:
                break;
            case R.id.btn_image_list:
                break;
        }
    }
}
