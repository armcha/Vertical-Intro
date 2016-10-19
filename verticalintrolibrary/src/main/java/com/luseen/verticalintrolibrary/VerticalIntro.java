package com.luseen.verticalintrolibrary;

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

import java.util.ArrayList;
import java.util.List;

public abstract class VerticalIntro extends AppCompatActivity {

    private List<VerticalIntroItem> verticalIntroItemList = new ArrayList<>();
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
        setContentView(R.layout.vertical_intro_activity);
        context = this;

        init();

        findViewsById();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), verticalIntroItemList);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(1.5);
        //verticalViewPager.setPageTransformer(false,new SimplePagerTransform());

        int firstPageBackgroundColor = verticalIntroItemList.get(1).getColor();
        bottomView.setBackgroundColor(ContextCompat.getColor(context, firstPageBackgroundColor));

        getScrollSpeed();

        verticalViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                super.onPageSelected(position);
                if (!isChangedFromClick) {
                    if (verticalViewPager.getCurrentItem() == verticalIntroItemList.size()) {
                        Log.e("onClick ", "Last Item reached");
                        ObjectAnimator o1 = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, bottomView.getHeight());
                        o1.setDuration(0);
                        o1.setStartDelay(100);
                        o1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                int currentBackgroundColor = ContextCompat.getColor(context,
                                        verticalIntroItemList.get(position + 1).getColor());
                                bottomView.setBackgroundColor(currentBackgroundColor);
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
                                    int currentBackgroundColor = ContextCompat.getColor(context,
                                            verticalIntroItemList.get(position + 1).getColor());
                                    bottomView.setBackgroundColor(currentBackgroundColor);
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
                                    int currentBackgroundColor = ContextCompat.getColor(context,
                                            verticalIntroItemList.get(position + 1).getColor());
                                    bottomView.setBackgroundColor(currentBackgroundColor);
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
                if (verticalViewPager.getCurrentItem() == verticalIntroItemList.size()) {
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
                            int currentBackgroundColor = ContextCompat.getColor(context,
                                    verticalIntroItemList.get(verticalViewPager.getCurrentItem() + 1).getColor());
                            bottomView.setBackgroundColor(currentBackgroundColor);
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

    private void getScrollSpeed() {
        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    oldX = event.getX();
                    oldY = event.getY();
                    scrollStartTime = System.currentTimeMillis();
                } else if (action == MotionEvent.ACTION_UP) {
                    long scrollTime = System.currentTimeMillis() - scrollStartTime;
                    newX = event.getX();
                    newY = event.getY();
                    double distance = Math.sqrt((newX - oldX) * (newX - oldX) + (newY - oldY) * (newY - oldY));
                    scrollSpeed = distance / scrollTime;
                }
                return false;
            }
        });
    }

    private void findViewsById() {
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
    }

    private boolean isGoingForward(int position) {
        return currentPosition < position;
    }

    protected void addVerticalIntroItem(VerticalIntroItem verticalIntroItem) {
        verticalIntroItemList.add(verticalIntroItem);
    }

    protected abstract void init();
}
