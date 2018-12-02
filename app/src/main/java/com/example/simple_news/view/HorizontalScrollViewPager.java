package com.example.simple_news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 陈金桁 on 2018/12/2.
 */

public class HorizontalScrollViewPager extends ViewPager {

    private float startX;
    private float startY;



    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(false);
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();

                float distanceX = endX - startX;
                float distenceY = endY - startY;

                if(Math.abs(distanceX) > Math.abs(distenceY)){
                    //水平方向
                    if(getCurrentItem() == 0 && distanceX > 0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(getCurrentItem() == (getAdapter().getCount() - 1) && distanceX < 0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //垂直方向
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
