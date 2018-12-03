package com.example.simple_news.menupager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.simple_news.R;
import com.example.simple_news.base.MenuDetaiBasePager;
import com.example.simple_news.domain.NewsBean;
import com.example.simple_news.domain.PhotosMenuDetailPagerBean;
import com.example.simple_news.utils.CacheUtils;
import com.example.simple_news.utils.Constants;
import com.example.simple_news.volley.VolleyManager;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 陈金桁 on 2018/12/1.
 */

public class PhotosMenuDetailPager extends MenuDetaiBasePager{
    private final NewsBean.DataBean detailPagerData;
    private ListView listView;
    private GridView gridView;
    private PhotosMenuDetailPagerAdapter adapter;

    private String url;
    private List<PhotosMenuDetailPagerBean.DataEntity.NewsEntity> news;

    public PhotosMenuDetailPager(Context context,NewsBean.DataBean detailPagerData) {
        super(context);
        this.detailPagerData = detailPagerData;
    }

    @Override
    public View initView() {
       View view = View.inflate(context, R.layout.photos_menudetail_pager,null);
        listView = (ListView) view.findViewById(R.id.listview);
        gridView = (GridView) view.findViewById(R.id.gridview);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        VolleyManager.init(context);
        url = Constants.Net + detailPagerData.getUrl();
        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
    }


    private void getDataFromNet() {
//        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                CacheUtils.putString(context,url,result);
                processData(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String parsed = new String(response.data, "UTF-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };

        VolleyManager.getRequestQueue().add(request);

    }

    private void processData(String json) {
        PhotosMenuDetailPagerBean bean = parsedJson(json);
        news = bean.getData().getNews();
        adapter = new PhotosMenuDetailPagerAdapter();
        listView.setAdapter(adapter);


    }

    class PhotosMenuDetailPagerAdapter extends BaseAdapter{

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
                convertView = View.inflate(context,R.layout.item_photos_menupager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            PhotosMenuDetailPagerBean.DataEntity.NewsEntity newsBean = news.get(position);

            viewHolder.tv_title.setText(newsBean.getTitle());

            String imageUrl = Constants.Net + newsBean.getSmallimage();

            loaderImager(viewHolder,imageUrl);

            return convertView;
        }
    }


    private void loaderImager(final ViewHolder viewHolder,String imageurl){
        viewHolder.iv_icon.setTag(imageurl);

        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if(imageContainer != null){
                    if(viewHolder.iv_icon != null){
                        if(imageContainer.getBitmap() != null){
                            viewHolder.iv_icon.setImageBitmap(imageContainer.getBitmap());
                        }else{
                            viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
                        }
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
            }
        };

        VolleyManager.getImageLoader().get(imageurl,listener);
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;

    }

    private PhotosMenuDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,PhotosMenuDetailPagerBean.class);
    }
}
