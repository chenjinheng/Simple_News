package com.example.simple_news.pager;

import android.content.Context;
import android.widget.TextView;

import com.example.simple_news.base.BasePager;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class NewsPager extends BasePager {
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


    }
}
