package com.example.simple_news.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simple_news.R;
import com.example.simple_news.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.activity_leftmenu);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        slidingMenu.setMode(SlidingMenu.LEFT);

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,200));
    }
}
