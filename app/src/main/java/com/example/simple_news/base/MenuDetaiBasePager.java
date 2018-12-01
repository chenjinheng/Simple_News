package com.example.simple_news.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public abstract class MenuDetaiBasePager {
    public Context context;

    public View rootView;

    public MenuDetaiBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    public void initData(){

    }

}
