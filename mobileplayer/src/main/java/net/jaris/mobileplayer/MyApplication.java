package net.jaris.mobileplayer;

import android.app.Application;

import org.xutils.*;
import org.xutils.BuildConfig;

/**
 * Created by Jaris on 2016/12/5.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }
}
