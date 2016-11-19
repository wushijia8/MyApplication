package net.jaris.jaris_code.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Jaris on 2016/11/7.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> mCache;

    public BitmapCache(){
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 从缓存中获取数据
     * @param s
     * @return
     */
    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }

    /**
     * 将数据放入缓存中
     * @param s
     * @param bitmap
     */
    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mCache.put(s,bitmap);
    }
}
