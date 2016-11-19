package net.jaris.jaris_code;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import net.jaris.jaris_code.okHttp.base.BaseFragment;
import net.jaris.jaris_code.fragment.CommonFrameFragment;
import net.jaris.jaris_code.fragment.CustomFragment;
import net.jaris.jaris_code.fragment.OtherFragment;
import net.jaris.jaris_code.fragment.ThirdPartFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaris on 2016/8/31.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class MainActivity extends FragmentActivity {

    private RadioGroup mRgMain;

    private List<BaseFragment> mBaseFragments;

    /**
     * 选择的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        //初始化Fragment
        initFragment();

        //设置RadioGroup监听事件
        setListener();
    }

    private void setListener() {
        mRgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中第一个
        mRgMain.check(R.id.rb_common_frame);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_common_frame:
                    position = 0;
                    break;
                case R.id.rb_thirdparty:
                    position = 1;
                    break;
                case R.id.rb_custom:
                    position = 2;
                    break;
                case R.id.rb_other:
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent,to);
        }
    }


    /**
     *
     * @param from：刚才显示的Fragment，即将要被隐藏
     * @param to：即将显示的Fragment
     */
    private void switchFragment(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断to是否被添加
            if(!to.isAdded()){
                //没有被添加
                if(from!=null){
                    ft.hide(from);
                }
                if(to!=null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else {
                if(from!=null){
                    ft.hide(from);
                }
                if(to!=null){
                    ft.show(to).commit();
                }
            }
        }
    }
    /*private void switchFragment(BaseFragment fragment) {
        //得到FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        //替换
        transaction.replace(R.id.fl_content,fragment);
        //提交
        transaction.commit();
    }*/

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        return mBaseFragments.get(position);
    }


    private void initFragment() {
        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(new CommonFrameFragment());
        mBaseFragments.add(new ThirdPartFragment());
        mBaseFragments.add(new CustomFragment());
        mBaseFragments.add(new OtherFragment());
    }

    /**
     * 初始化View
     */
    private void initView() {
        setContentView(R.layout.activity_main);

        mRgMain = (RadioGroup) findViewById(R.id.rg_main);

    }
}
