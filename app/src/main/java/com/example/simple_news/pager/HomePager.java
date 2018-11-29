package com.example.simple_news.pager;

import android.content.Context;
import android.widget.TextView;

import com.example.simple_news.base.BasePager;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class HomePager extends BasePager {
    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("主界面");

        TextView textView = new TextView(context);
        textView.setText("我是主界面");
        fl_content.addView(textView);


    }
}
