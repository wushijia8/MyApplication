package net.jaris.jaris_code.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import net.jaris.jaris_code.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterKnifeActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_butterknife)
    TextView tvButterknife;
    @Bind(R.id.cb_butterknife)
    CheckBox cbButterknife;
    @Bind(R.id.bt_butterknife)
    Button btButterknife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("ButterKnife");
        tvButterknife.setText("使用ButterKnife");
    }

    @OnClick(R.id.cb_butterknife)
    void cbButterKnife(View v){
        Toast.makeText(this, "点击了checkbox", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_butterknife)
    void btButterKnife(View v){
        Toast.makeText(this, "点击了button", Toast.LENGTH_SHORT).show();
    }
}
