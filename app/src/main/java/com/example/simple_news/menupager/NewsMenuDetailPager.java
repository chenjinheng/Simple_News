package com.example.simple_news.menupager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.base.MenuDetaiBasePager;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class NewsMenuDetailPager  extends MenuDetaiBasePager{

    private ViewPager viewPager;

    public NewsMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager,null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

    }
}
