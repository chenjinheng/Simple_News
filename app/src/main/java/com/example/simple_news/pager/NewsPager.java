package com.example.simple_news.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.simple_news.activity.MainActivity;
import com.example.simple_news.base.BasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.fragment.LeftmenuFragment;
import com.example.simple_news.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class NewsPager extends BasePager {

    private List<NewsBean.DataBean> data;

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

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.news_center_url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

        MainActivity mainActivity = (MainActivity) context;
        LeftmenuFragment leftmenuFragment =  mainActivity.getLeftMenuFragment();

        leftmenuFragment.setData(data);
    }

    private NewsBean parsedJson(String result) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(result,NewsBean.class);
        return newsBean;
    }
}
