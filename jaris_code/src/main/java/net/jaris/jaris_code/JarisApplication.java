package net.jaris.jaris_code;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.x;


/**
 * Created by Jaris on 2016/11/1.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class JarisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xUtils3初始化
        x.Ext.init(this);
        //是否输出debug日志，开启debug会影响性能
        x.Ext.setDebug(true);

        //初始化ImageLoader
        initImageloader(this);

        //初始化Fresco
        initFresco(this);
    }

    private void initFresco(Context context) {
        Fresco.initialize(context);
    }

    private void initImageloader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)//线程优先级
                .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片缓存到内存时，只缓存一个，默认会缓存多个不同大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存时候的URI名称用MD5
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置图片下载和显示的工作队列排序
                .writeDebugLogs()//打印debug log
                .build();

        //全局初始化配置
        ImageLoader.getInstance().init(config);
    }
}
