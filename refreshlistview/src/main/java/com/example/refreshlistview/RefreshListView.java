package com.example.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 陈金桁 on 2018/12/2.
 */

public class RefreshListView extends ListView {
    private LinearLayout headerView;
    private View ll_pull_down_fresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_status;
    private TextView tv_time;
    private int headerHeight;

    private onRefreshListener onRefreshListener;


    public static final int PULL_DOWN_REFRESH = 0;
    public static final int RELEASE_REFRESH = 1;
    public static final int REFRESHING = 2;

    private int currentStatus = PULL_DOWN_REFRESH;

    private Animation upAnimation;
    private Animation downAnimation;
    private View footView;
    private int footerViewHeight;
    private boolean isLoadMore = false;
    private View topNewsView;
    private int listViewOnScreenY = -1;


    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFootView(context);
    }

    private void initFootView(Context context) {
         footView = View.inflate(context,R.layout.refresh_footer,null);
        footView.measure(0,0);
        footerViewHeight = footView.getMeasuredWidth();

        footView.setPadding(0,-footerViewHeight,0,0);

        addFooterView(footView);

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING){
                    if(getLastVisiblePosition() >= getCount() - 1){
                        footView.setPadding(8,8,8,8);
                        isLoadMore = true;

                        if(onRefreshListener != null){
                            onRefreshListener.onLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initHeaderView(Context context) {
         headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header,null);
        ll_pull_down_fresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        ll_pull_down_fresh.measure(0,0);
        headerHeight = ll_pull_down_fresh.getMeasuredHeight();

        ll_pull_down_fresh.setPadding(0,-headerHeight,0,0);


        addHeaderView(headerView);
    }

    private float startY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(startY == -1){
                    startY = ev.getY();
                }
                boolean isDisplayTopNews = isDisplayTopNewss();
                if(!isDisplayTopNews){
                    break;
                }
                if(currentStatus ==REFRESHING){
                    break;
                }

                float endY = ev.getY();
                float distanceY = endY - startY;
                if(distanceY > 0){
                    int paddingTop = (int) (-headerHeight + distanceY);
                    ll_pull_down_fresh.setPadding(0,paddingTop,0,0);
                    if(paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH){
                        currentStatus = PULL_DOWN_REFRESH;
                        refreshViewState();

                    }else if(paddingTop > 0 && currentStatus != RELEASE_REFRESH){
                        currentStatus = RELEASE_REFRESH;
                        refreshViewState();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentStatus == PULL_DOWN_REFRESH){
                    ll_pull_down_fresh.setPadding(0,-headerHeight,0,0);

                }else if(currentStatus == RELEASE_REFRESH){
                    currentStatus = REFRESHING;
                    ll_pull_down_fresh.setPadding(0,0,0,0);
                    refreshViewState();

                    if(onRefreshListener != null){
                        onRefreshListener.onPullDownRefresh();
                    }
                }
                break;


        }

        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNewss() {
        if(topNewsView != null){
            int [] location = new int[2];
            if(listViewOnScreenY == -1) {
                getLocationOnScreen(location);
                listViewOnScreenY = location[1];
            }

            topNewsView.getLocationOnScreen(location);
            int topNewsViewOnScreenY = location[1];

            return listViewOnScreenY <= topNewsViewOnScreenY;
        }else{
            return true;
        }
    }


    private void initAnimation(){
        upAnimation = new RotateAnimation(0,-180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180,-360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }
    private void refreshViewState() {
        switch (currentStatus){
            case PULL_DOWN_REFRESH:
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新...");

                break;
            case RELEASE_REFRESH:
                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("手松刷新...");
                break;
            case REFRESHING:
                tv_status.setText("正在刷新");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }

    }

    public void onRefreshFinish(boolean b) {
        if(isLoadMore){
            isLoadMore = false;

            footView.setPadding(0,-footerViewHeight,0,0);
        }else{
            tv_status.setText("下拉刷新...");
            currentStatus = PULL_DOWN_REFRESH;
            iv_arrow.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_arrow.setVisibility(VISIBLE);

            ll_pull_down_fresh.setPadding(0,-headerHeight,0,0);
            if(b){
                tv_time.setText("上次更新时间" + getSystemTime());
            }
        }

    }

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public void addTopNewsView(View topNewsView) {
        if(topNewsView != null){
            this.topNewsView = topNewsView;
            headerView.addView(topNewsView);
        }

    }

    public interface onRefreshListener{
        void onPullDownRefresh();

        void onLoadMore();
    }

    public void setOnRefreshListener(onRefreshListener l){
        this.onRefreshListener = l;
    }
}
