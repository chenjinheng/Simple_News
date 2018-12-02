package com.example.simple_news.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.simple_news.activity.MainActivity;
import com.example.simple_news.base.BasePager;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.fragment.LeftmenuFragment;
import com.example.simple_news.menupager.NewsMenuDetailPager;
import com.example.simple_news.menupager.PhotosMenuDetailPager;
import com.example.simple_news.menupager.TopicMenuDetailPager;
import com.example.simple_news.menupager.interacMenuDetailPager;
import com.example.simple_news.utils.CacheUtils;
import com.example.simple_news.utils.Constants;
import com.example.simple_news.utils.OkhttpUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class NewsPager extends BasePager {

    private List<NewsBean.DataBean> data;

    private ArrayList<MenuDetaiBasePager> detaiBasePagers;
    private final int PROCESS_JSON = 1;
    private String responseData;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");

        TextView textView = new TextView(context);
        textView.setText("我是新闻");
        fl_content.addView(textView);

        ib_menu.setVisibility(View.VISIBLE);

        String saveJson = CacheUtils.getString(context,Constants.news_center_url);

        if (!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }else{
            getDataFromNet();
        }

    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.news_center_url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                Log.e("onError", result);
                CacheUtils.putString(context,Constants.news_center_url,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError", "AAA"+ ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        NewsBean bean = parsedJson(result);

        data = bean.getData();
        Log.e("TAG",data.get(0).getTitle());
        MainActivity mainActivity = (MainActivity) context;
        LeftmenuFragment leftmenuFragment =  mainActivity.getLeftMenuFragment();


        detaiBasePagers = new ArrayList<>();

        detaiBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));
        detaiBasePagers.add(new TopicMenuDetailPager((context)));
        detaiBasePagers.add(new PhotosMenuDetailPager(context));
        detaiBasePagers.add(new interacMenuDetailPager(context));

        leftmenuFragment.setData(data);


    }

    private NewsBean parsedJson(String result) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(result,NewsBean.class);
        return newsBean;
    }

    public void swichPager(int position) {
        tv_title.setText(data.get(position).getTitle());

        fl_content.removeAllViews();

        MenuDetaiBasePager menuDetaiBasePager = detaiBasePagers.get(position);
        View rootView = menuDetaiBasePager.rootView;
        fl_content.addView(rootView);
        menuDetaiBasePager.initData();

    }
}
