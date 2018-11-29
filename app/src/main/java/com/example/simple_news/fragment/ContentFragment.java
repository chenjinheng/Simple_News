package com.example.simple_news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.base.BaseFragment;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class ContentFragment extends BaseFragment {

    private RadioGroup rg_main;
    private ViewPager viewPager;



    @Override
    public View initView() {
       View view = View.inflate(context, R.layout.content_fragment,null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        return view;
    }

    @Override
    public void initData(){
        super.initData();
        rg_main.check(R.id.rb_home);
    }
}
