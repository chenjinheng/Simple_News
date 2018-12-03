package com.example.simple_news.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_news.R;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTestsize;
    private ImageButton ibShare;

    private WebView webView;
    private ProgressBar progressBar;
    private String url;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-03 10:40:23 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvTitle = (TextView)findViewById( R.id.tv_title );
        ibMenu = (ImageButton)findViewById( R.id.ib_menu );
        ibBack = (ImageButton)findViewById( R.id.ib_back );
        ibTestsize = (ImageButton)findViewById( R.id.ib_testsize );
        ibShare = (ImageButton)findViewById( R.id.ib_share );
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        tvTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);

        ibBack.setVisibility(View.VISIBLE);
        ibTestsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibMenu.setOnClickListener( this );
        ibBack.setOnClickListener( this );
        ibTestsize.setOnClickListener( this );
        ibShare.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
         if ( v == ibBack ) {
            // Handle clicks for ibBack
             finish();
        } else if ( v == ibTestsize ) {
            // Handle clicks for ibTestsize
             Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
        } else if ( v == ibShare ) {
            // Handle clicks for ibShare
             Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        findViews();
        getData();
    }

    private void getData() {
       url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        webView.getSettings();
    }
}
