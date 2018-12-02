package com.example.simple_news.menupager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.domain.TabDetailBean;
import com.example.simple_news.utils.CacheUtils;
import com.example.simple_news.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URL;
import java.util.List;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class TabDetailPager extends MenuDetaiBasePager {
    private ViewPager viewPager;
    private TextView iv_title;
    private LinearLayout ll_point_group;
    private ListView listView;


    private NewsBean.DataBean.ChildrenBean childrenBean;

    private String url;
    private List<TabDetailBean.DataBean.NewsBean> topnews;

    public TabDetailPager(Context context,NewsBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;

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
        topnews = tabDetailBean.getData().getNews();

        Log.e("AAG",topnews.get(0).getTitle());

        viewPager.setAdapter(new MyTabPagerAdaper());
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
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context,url,result);
                Log.e("onSuccess",result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError",ex.getMessage());
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
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ll_point_group = (LinearLayout) view.findViewById(R.id.ll_point_group);
        iv_title = (TextView) view.findViewById(R.id.tv_title);
        listView = (ListView) view.findViewById(R.id.listview);

        return view;
    }
}
