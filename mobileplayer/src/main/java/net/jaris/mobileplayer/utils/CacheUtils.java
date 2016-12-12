package net.jaris.mobileplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.jaris.mobileplayer.service.MusicPlayerService;

/**
 * Created by Jaris on 2016/12/6.
 * 微信:weixin-wushijia
 * QQ:1020392881
 * 邮箱：jaris.w@foxmail.com
 */

public class CacheUtils {

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("jaris", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 得到缓存的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("jaris", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * 保存播放模式
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putPlayMode(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("jaris", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 得到播放模式
     * @param context
     * @param key
     * @return
     */
    public static int getPlayMode(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("jaris",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, MusicPlayerService.REPRAT_NORMAL);
    }
}
