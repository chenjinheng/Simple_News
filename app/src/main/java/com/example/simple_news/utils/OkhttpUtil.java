package com.example.simple_news.utils;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class OkhttpUtil {
    private static String responseData;
    private static Executor executor = Executors.newSingleThreadExecutor();
    public static Call getResponseData(String url){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(Constants.news_center_url)
//                        .build();
//                try {
//                    Response response = okHttpClient.newCall(request).execute();
//                    responseData = response.body().string();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        executor.execute(runnable);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);


        return call;
    }
}
