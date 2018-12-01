package com.example.simple_news.menupager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.simple_news.base.MenuDetaiBasePager;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class interacMenuDetailPager extends MenuDetaiBasePager{
    private TextView textView;

    public interacMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("互动详情");
    }
}
