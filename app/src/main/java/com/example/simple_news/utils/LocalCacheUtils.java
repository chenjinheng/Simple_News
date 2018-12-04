package com.example.simple_news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by 陈金桁 on 2018/12/4.
 */
public class LocalCacheUtils {
    private final MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5Encoder.encode(imageUrl);

                File file = new File(Environment.getExternalStorageDirectory() + "/newsdata",fileName);

                if(file.exists()){
                    FileInputStream is = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if(bitmap != null){
                        memoryCacheUtils.putBitmap(imageUrl,bitmap);
                    }
                    return bitmap;
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        return null;
    }

    public void putBitmap(String imageUrl, Bitmap bitmap) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5Encoder.encode(imageUrl);

                File file = new File(Environment.getExternalStorageDirectory() + "/newsdata",fileName);

                if(!file.exists()){
                    file.createNewFile();
                }


                bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
