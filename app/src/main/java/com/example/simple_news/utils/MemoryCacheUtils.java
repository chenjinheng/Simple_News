package com.example.simple_news.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by 陈金桁 on 2018/12/4.
 */
public class MemoryCacheUtils {

    private LruCache<String,Bitmap> lruCache;

    public MemoryCacheUtils(){


        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        return lruCache.get(imageUrl);
    }



    public void putBitmap(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl,bitmap);
    }
}
