package com.luseen.verticalIntro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager verticalViewPager;
    private RelativeLayout bottomView;
    private Context context;
    private double newX, newY, oldX, oldY;
    private double scrollSpeed;
    private int currentPosition;
    private long scrollStartTime;
    private boolean isChangedFromClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        final int[] colors = {R.color.colorAccent, R.color.color2, R.color.color3,
                R.color.colorPrimary, R.color.colorPrimaryDark};
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), colors);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(1.5);
        //verticalViewPager.setPageTransformer(false,new SimplePagerTransform());
        bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[1]));


        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    oldX = event.getX();
                    oldY = event.getY();
                    scrollStartTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    long scrollEndTime = System.currentTimeMillis() - scrollStartTime;
                    newX = event.getX();
                    newY = event.getY();
                    double distance = Math.sqrt((newX - oldX) * (newX - oldX) + (newY - oldY) * (newY - oldY));
                    scrollSpeed = distance / scrollEndTime;
                }
                return false;
            }
        });

        verticalViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (!isChangedFromClick) {
                    if (verticalViewPager.getCurrentItem() == colors.length - 2) {
                        Log.e("onClick ", "Last Item reached");
                        ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                        o1.setDuration(0);
                        o1.setStartDelay(100);
                        o1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[verticalViewPager.getCurrentItem() + 1]));
                                ObjectAnimator o = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0);
                                o.setDuration((long) (1000 / (scrollSpeed)));
                                o.start();
                            }
                        });
                        o1.start();
                    } else {
                        //verticalViewPager.setCurrentItem(verticalViewPager.getCurrentItem() + 1);
                        if (isGoingForward(position)) {
                            ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                            o1.setDuration(0);
                            o1.setStartDelay(100);
                            o1.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[verticalViewPager.getCurrentItem() + 1]));
                                    ObjectAnimator o = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0);
                                    o.setDuration((long) (800 / (scrollSpeed)));
                                    o.start();
                                }
                            });
                            o1.start();
                        } else {
                            ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                            o1.setDuration((long) (600 / (scrollSpeed)));
                            // o1.setStartDelay(100);
                            o1.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    bottomView.setBackgroundColor(ContextCompat.getColor(context, colors[verticalViewPager.getCurrentItem() + 1]));
                                    ObjectAnimator o = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0).setDuration(0);
                                    o.start();
                                }
                            });
                            o1.start();
                        }
                    }
                } else {
                    isChangedFromClick = false;
                    Log.e("onPageSelected ", "from click");
                }
                currentPosition = position;
            }
        });

        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verticalViewPager.getCurrentItem() == colors.length - 2) {
                    Log.e("onClick ", "Last Item reached");
                } else {
                    isChangedFromClick = true;
                    verticalViewPager.setScrollDurationFactor(4);
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
                            o.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    verticalViewPager.setScrollDurationFactor(1.5);
                                }
                            });
                            o.start();
                        }
                    });
                    o1.start();
                }
            }

        });
    }

    private boolean isGoingForward(int position) {
        return currentPosition < position;
    }
}
