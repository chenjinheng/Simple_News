package com.example.simple_news.utils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * Created by 陈金桁 on 2018/12/4.
 */

public class BitmapCacheUtils {

    private NetCacheUtils netCacheUtils;

    private LocalCacheUtils localCacheUtils;

    private MemoryCacheUtils memoryCacheUtils;

    public BitmapCacheUtils(Handler handler){
        memoryCacheUtils = new MemoryCacheUtils();

        localCacheUtils = new LocalCacheUtils(memoryCacheUtils);

        netCacheUtils = new NetCacheUtils(handler,localCacheUtils,memoryCacheUtils);
    }

    public Bitmap getBitmap(String imageUrl, int position) {

        if(memoryCacheUtils != null){
            Bitmap bitmap = memoryCacheUtils.getBitmapFromUrl(imageUrl);
            if(bitmap != null){
                return bitmap;
            }
        }


        if(localCacheUtils != null){
            Bitmap bitmap = localCacheUtils.getBitmapFromUrl(imageUrl);
            if(bitmap != null){
                return bitmap;
            }
        }


        netCacheUtils.getBitmapFromNew(imageUrl,position);

        return null;
    }
}
