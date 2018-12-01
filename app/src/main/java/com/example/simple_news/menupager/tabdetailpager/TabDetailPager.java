package com.example.simple_news.menupager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class TabDetailPager extends MenuDetaiBasePager {
    private NewsBean.DataBean.ChildrenBean childrenBean;



    public TabDetailPager(Context context,NewsBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;

    }


    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        return view;
    }
}
