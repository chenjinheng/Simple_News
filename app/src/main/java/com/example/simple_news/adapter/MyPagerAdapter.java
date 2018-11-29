package com.example.simple_news.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.simple_news.base.BasePager;

import java.util.ArrayList;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<BasePager> basePagers;

    public MyPagerAdapter(ArrayList<BasePager> basePagers) {
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = basePagers.get(position);
        View rootView = basePager.viewRoot;
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
