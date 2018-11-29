package com.example.simple_news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simple_news.base.BaseFragment;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class LeftmenuFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;
    }

    @Override
    public void initData(){
        super.initData();
        textView.setText("左侧菜单");
    }
}
