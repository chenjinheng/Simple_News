package com.example.simple_news.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.activity.MainActivity;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class BasePager {
    public Context context;
    public View viewRoot;

    public TextView tv_title;

    public ImageButton ib_menu;

    public FrameLayout fl_content;

    public BasePager(Context context) {
        this.context = context;
        viewRoot = initView();
    }

    private View initView() {
        View view = View.inflate(context, R.layout.base_pager,null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return view;
    }

    public void initData(){

    }

}
