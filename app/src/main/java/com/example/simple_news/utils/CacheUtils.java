package com.example.simple_news.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.example.simple_news.LauncherActivity;
import com.example.simple_news.view.GuideActivity;

/**
 * Created by 陈金桁 on 2018/11/28.
 */

public class CacheUtils {
    public static boolean getBoolean(Context context, String start_main) {
        SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return sp.getBoolean(start_main,false);
    }

    public static void putBoolean(Context context, String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,b).commit();
    }
}
