package com.example.simple_news.menupager.tabdetailpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.refreshlistview.RefreshListView;
import com.example.simple_news.R;
import com.example.simple_news.activity.NewsDetailActivity;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.domain.TabDetailBean;
import com.example.simple_news.utils.CacheUtils;
import com.example.simple_news.utils.Constants;
import com.example.simple_news.utils.DensityUtil;
import com.example.simple_news.view.HorizontalScrollViewPager;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.URL;
import java.util.List;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class TabDetailPager extends MenuDetaiBasePager {
    public static final String READ_ARRAY_ID = "read_array_id";
    private HorizontalScrollViewPager viewPager;
    private TextView iv_title;
    private LinearLayout ll_point_group;
    private RefreshListView listView;
    private MyTabDetailListAdapter adapter;
    private ImageOptions imageOptions;

    private String moreUrl;


    private NewsBean.DataBean.ChildrenBean childrenBean;

    private String url;
    private List<TabDetailBean.DataBean.NewsBean> topnews;
    private List<TabDetailBean.DataBean.NewsBean> news;
    private List<TabDetailBean.DataBean.NewsBean> moreNews;

    private boolean isLoadMore = false;

    public TabDetailPager(Context context,NewsBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
        imageOptions = new ImageOptions.Builder()
                .setSize(org.xutils.common.util.DensityUtil.dip2px(100), org.xutils.common.util.DensityUtil.dip2px(100))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(5))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }


    @Override
    public void initData() {
        super.initData();
        url = Constants.Net + childrenBean.getUrl();
        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
    }

    private void processData(String saveJson) {
        TabDetailBean tabDetailBean = parsedJson(saveJson);

        moreUrl = "";
        if(TextUtils.isEmpty(tabDetailBean.getData().getMore())){
            moreUrl = "";
        }else{
            moreUrl = Constants.Net + tabDetailBean.getData().getMore();
        }

        if(!isLoadMore){
            topnews = tabDetailBean.getData().getNews();

            viewPager.setAdapter(new MyTabPagerAdaper());

            ll_point_group.removeAllViews();

            for(int i = 0;i < topnews.size();i++){
                ImageView imageView = new ImageView(context);
                imageView.setBackgroundResource(R.drawable.point_selector);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context,8),DensityUtil.dip2px(context,8));

                if(i == 0){
                    imageView.setEnabled(true);
                }else{
                    imageView.setEnabled(false);
                    params.leftMargin = DensityUtil.dip2px(context,8);
                }

                imageView.setLayoutParams(params);
                ll_point_group.addView(imageView);

            }
            iv_title.setText(topnews.get(prePosition).getTitle());
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

            news = tabDetailBean.getData().getNews();
            adapter = new MyTabDetailListAdapter();
            listView.setAdapter(adapter);
        }else{
            isLoadMore = false;
             moreNews = tabDetailBean.getData().getNews();
            news.addAll(moreNews);

            adapter.notifyDataSetChanged();
        }


    }
    class MyTabDetailListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context,R.layout.item_tabletail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);
            }else{
               viewHolder = (ViewHolder) convertView.getTag();
            }

            TabDetailBean.DataBean.NewsBean newsBean = news.get(position);
            String imageUrl = Constants.Net + newsBean.getListimage();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.news_pic_default)
                    .error(R.drawable.news_pic_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.iv_icon);

//            x.image().bind(viewHolder.iv_icon,imageUrl,imageOptions);



            viewHolder.tv_title.setText(newsBean.getTitle());
            viewHolder.tv_time.setText(newsBean.getPubdate());

            String idArray = CacheUtils.getString(context,READ_ARRAY_ID);
            if(idArray.contains(newsBean.getId() + "")){
                viewHolder.tv_title.setTextColor(Color.GRAY);
            }else{
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }
    private int prePosition;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            iv_title.setText(topnews.get(position).getTitle());
            ll_point_group.getChildAt(prePosition).setEnabled(false);

            ll_point_group.getChildAt(position).setEnabled(true);

            prePosition = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyTabPagerAdaper extends PagerAdapter{

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            container.addView(imageView);

            TabDetailBean.DataBean.NewsBean topnewsData = topnews.get(position);
            String imageUrl = Constants.Net + topnewsData.getListimage();
            x.image().bind(imageView,imageUrl);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private TabDetailBean parsedJson(String saveJson) {
        return new Gson().fromJson(saveJson,TabDetailBean.class);
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context,url,result);
                processData(result);
                listView.onRefreshFinish(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError",ex.getMessage());
                listView.onRefreshFinish(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        listView = (RefreshListView) view.findViewById(R.id.listview);
        listView.setOnRefreshListener(new RefreshListView.onRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                getDataFromNet();
            }

            @Override
            public void onLoadMore() {
                if(TextUtils.isEmpty(moreUrl)){
                    Toast.makeText(context, "没有更多数据...", Toast.LENGTH_SHORT).show();
                    listView.onRefreshFinish(false);
                }else{
                    getMoreDataFromNet();
                }

            }
        });

        View topNewsView = View.inflate(context,R.layout.topnews,null);
        ll_point_group = (LinearLayout) topNewsView.findViewById(R.id.ll_point_group);
        viewPager = (HorizontalScrollViewPager) topNewsView.findViewById(R.id.viewpager);
        iv_title = (TextView) topNewsView.findViewById(R.id.tv_title);


//        listView.addHeaderView(topNewsView);

        listView.addTopNewsView(topNewsView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int realPosition = position - 1;
                TabDetailBean.DataBean.NewsBean newsBean = news.get(realPosition);

                String idArray = CacheUtils.getString(context, READ_ARRAY_ID);

                if(!idArray.contains(newsBean.getId() + "")){
                    CacheUtils.putString(context,READ_ARRAY_ID,idArray + newsBean.getId() + ",");
                    adapter.notifyDataSetChanged(); //调用此方法，会调用适配器的getCount()和getView()方法进行界面刷新
                }

                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("url",Constants.Net + newsBean.getUrl());
                context.startActivity(intent);
            }
        });
        return view;
    }

    private void getMoreDataFromNet() {
        RequestParams params = new RequestParams(moreUrl);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                isLoadMore = true;
                processData(result);
                listView.onRefreshFinish(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listView.onRefreshFinish(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
