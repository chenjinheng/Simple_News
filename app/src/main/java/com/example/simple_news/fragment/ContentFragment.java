package com.example.simple_news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.activity.MainActivity;
import com.example.simple_news.base.BaseFragment;
import com.example.simple_news.base.BasePager;
import com.example.simple_news.pager.GovaffairPager;
import com.example.simple_news.pager.HomePager;
import com.example.simple_news.pager.NewsPager;
import com.example.simple_news.pager.ServicePager;
import com.example.simple_news.pager.SettingPager;
import com.example.simple_news.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.example.simple_news.adapter.MyPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class ContentFragment extends BaseFragment {

    private RadioGroup rg_main;
    private NoScrollViewPager viewPager;

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
       View view = View.inflate(context, R.layout.content_fragment,null);
        viewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        return view;
    }

    @Override
    public void initData(){
        super.initData();
        rg_main.check(R.id.rb_home);
        basePagers = new ArrayList<>();

        basePagers.add(new HomePager(context));
        basePagers.add(new NewsPager(context));
        basePagers.add(new ServicePager(context));
        basePagers.add(new GovaffairPager(context));
        basePagers.add(new SettingPager(context));
        basePagers.get(0).initData();

        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);


        viewPager.setAdapter(new MyPagerAdapter(basePagers));
        viewPager.addOnPageChangeListener(new MyOnPagerChangeListener());

        rg_main.setOnCheckedChangeListener(new MyCheckedChangeListener());
    }
    class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:
                    viewPager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:
                    viewPager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    private void isEnableSlidingMenu(int touchmodeFullScreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullScreen);
    }


}
