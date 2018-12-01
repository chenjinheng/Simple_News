package com.example.simple_news.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simple_news.R;
import com.example.simple_news.activity.MainActivity;
import com.example.simple_news.base.BaseFragment;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.pager.NewsPager;
import com.example.simple_news.utils.DensityUtil;

import java.util.List;

/**
 * Created by 陈金桁 on 2018/11/29.
 */

public class LeftmenuFragment extends BaseFragment {
    private List<NewsBean.DataBean> data;

    private LeftmenuFragmentAdapter leftmenuFragmentAdapter = new LeftmenuFragmentAdapter();

    private ListView listView;

    private int prePosition;

    @Override
    public View initView() {
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(Color.TRANSPARENT);
        listView.setDividerHeight(0);

        listView.setSelector(android.R.color.transparent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prePosition = position;
                leftmenuFragmentAdapter.notifyDataSetChanged();



                switcager(prePosition);
            }
        });

        return listView;
    }

    private void switcager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().toggle();
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsPager newsPager = contentFragment.getNewsPager();
        newsPager.swichPager(position);
    }

    @Override
    public void initData(){
        super.initData();

    }

    public void setData(List<NewsBean.DataBean> data) {
        this.data = data;

        switcager(prePosition);

        listView.setAdapter(leftmenuFragmentAdapter);
    }
    class LeftmenuFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
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
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu,null);
            textView.setText(data.get(position).getTitle());
            textView.setEnabled(prePosition == position);
            return textView;
        }
    }
}
