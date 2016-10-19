package com.luseen.verticalintro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout bottomView;
    private Context context;
    private VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        final int[] colors = {R.color.colorAccent, R.color.color2, R.color.color3, R.color.colorPrimary, R.color.colorPrimaryDark};
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), colors);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(3.5);
        //verticalViewPager.setPageTransformer(false,new SimplePagerTransform());
        bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[1]));

        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (verticalViewPager.getCurrentItem() == colors.length - 2) {
                    Log.e("onClick ", "Last Item reached");
                } else {
                    //verticalViewPager.setCurrentItem(verticalViewPager.getCurrentItem() + 1);
                    Log.e("onPageSelected ", "" + isGoingForward(position));
                    ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                    o1.setDuration(0);
                    o1.setStartDelay(100);
                    o1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[verticalViewPager.getCurrentItem() + 1]));
                            ObjectAnimator o = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0).setDuration(600);
                            o.start();
                        }
                    });
                    o1.start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                switch (state) {
//                    case ViewPager.SCROLL_STATE_IDLE:
//                        Log.e("SCROLL_STATE_IDLE ", "SCROLL_STATE_IDLE");
//                        break;
//                    case ViewPager.SCROLL_STATE_DRAGGING:
//                        Log.e("SCROLL_STATE_DRAGGING ", "SCROLL_STATE_DRAGGING");
//                        break;
//                    case ViewPager.SCROLL_STATE_SETTLING:
//                        Log.e("SCROLL_STATE_SETTLING ", "SCROLL_STATE_SETTLING");
//                        break;
//                }

            }
        });

        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verticalViewPager.getCurrentItem() == colors.length - 2) {
                    Log.e("onClick ", "Last Item reached");
                } else {
                    verticalViewPager.setCurrentItem(verticalViewPager.getCurrentItem() + 1);

                    ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                    o1.setDuration(0);
                    o1.setStartDelay(100);
                    o1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[verticalViewPager.getCurrentItem() + 1]));
                            ObjectAnimator o = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0).setDuration(600);
                            o.start();
                        }
                    });
                    o1.start();
                }
            }

        });
    }

    private boolean isGoingForward(int currentPosition) {
        return verticalViewPager.getCurrentItem() == currentPosition;
    }
}
