package com.example.simple_news.menupager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.activity.MainActivity;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.menupager.tabdetailpager.TabDetailPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class NewsMenuDetailPager  extends MenuDetaiBasePager{
    private TabPageIndicator tabPageIndicator;
    private ViewPager viewPager;
    private List<NewsBean.DataBean.ChildrenBean> children;
    private ArrayList<TabDetailPager> tabDetailPagers;
    private ImageButton tab_next;

    public NewsMenuDetailPager(Context context, NewsBean.DataBean dataBean) {
        super(context);
        children = dataBean.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager,null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tabPageIndicator);
        tab_next = (ImageButton) view.findViewById(R.id.tab_next);
        tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tabDetailPagers = new ArrayList<>();
        for(int i = 0;i < children.size();i++){
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }

        viewPager.setAdapter(new MyNewMenuPagerAdaper());

        tabPageIndicator.setViewPager(viewPager);

        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    private void isEnableSlidingMenu(int touchmodeFullScreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullScreen);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else{
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyNewMenuPagerAdaper extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View view = tabDetailPager.rootView;
            container.addView(view);
            tabDetailPager.initData();
            return view;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
