package com.example.simple_news.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    public static void putString(Context context, String key, String value) {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5Encoder.encode(key);

                File file = new File(Environment.getExternalStorageDirectory() + "/newsdata/files",fileName);

                File parentFile = file.getParentFile();

                if(!file.exists()){
                    file.createNewFile();
                }

                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            sp.edit().putString(key,value).commit();
        }


    }

    public static String getString(Context context,String key) {
        String result = "";

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5Encoder.encode(key);

                File file = new File(Environment.getExternalStorageDirectory() + "/newsdata/files",fileName);

                if(file.exists()){
                    FileInputStream is = new FileInputStream(file);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];

                    int length;

                    while((length = is.read(buffer)) != -1){
                        stream.write(buffer,0,length);
                    }

                    is.close();
                    stream.close();
                    result = stream.toString();

                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }else{

            SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            result = sp.getString(key,"");

        }
        return result;
    }
}
