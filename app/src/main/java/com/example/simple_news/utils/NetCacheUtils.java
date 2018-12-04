package com.example.simple_news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 陈金桁 on 2018/12/4.
 */

public class NetCacheUtils {
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    private final Handler handler;
    private final LocalCacheUtils localCacheUtils;
    private final MemoryCacheUtils memoryCacheUtils;
    private ExecutorService service;

    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.handler = handler;
        service = Executors.newFixedThreadPool(10);
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNew(String imageUrl, int position) {
        service.execute(new MyRunnable(imageUrl,position));
    }

    class MyRunnable implements Runnable{
        private final String imageUrl;
        private final int position;

        public MyRunnable(String imageUrl, int position){
            this.imageUrl = imageUrl;
            this.position = position;
        }
        @Override
        public void run() {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(4000);
                connection.setConnectTimeout(4000);
                connection.connect();
                int code = connection.getResponseCode();
                if(code == 200){
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    msg.arg1 = position;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                    memoryCacheUtils.putBitmap(imageUrl,bitmap);

                    localCacheUtils.putBitmap(imageUrl,bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = FAIL;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        }
    }
}
