package com.example.simple_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.simple_news.utils.CacheUtils;
import com.example.simple_news.activity.GuideActivity;
import com.example.simple_news.activity.MainActivity;

public class LauncherActivity extends AppCompatActivity {
    private RelativeLayout activity_launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        initViews();

        setAnimation();
    }

    private void setAnimation() {
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.setDuration(2000);

        set.setAnimationListener(new MyAnimationListener());

        activity_launcher.startAnimation(set);
    }

    private void initViews() {
        activity_launcher = (RelativeLayout) findViewById(R.id.activity_launcher);
    }

    class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            boolean isStartMain = CacheUtils.getBoolean(LauncherActivity.this,"start_main");
            Intent intent;
            if(isStartMain){
                intent = new Intent(LauncherActivity.this,MainActivity.class);

            }else{
                intent = new Intent(LauncherActivity.this,GuideActivity.class);

            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
