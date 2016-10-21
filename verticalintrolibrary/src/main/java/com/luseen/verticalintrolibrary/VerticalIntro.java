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

    public static final String TAG = "VerticalIntro";
    private List<VerticalIntroItem> verticalIntroItemList = new ArrayList<>();
    private VerticalViewPager verticalViewPager;
    private RelativeLayout bottomView;
    private Context context;
    private double newX, newY, oldX, oldY;
    private long scrollStartTime;
    private double scrollSpeed;
    private int currentPosition;
    private boolean isChangedFromClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_intro_activity);
        context = this;

        init();

        findViews();

        setUpViewPager();

        setUpBottomView();

        getScrollSpeed();

        addListeners();
    }

    private View.OnClickListener bottomButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (verticalViewPager.getCurrentItem() == verticalIntroItemList.size() - 1) {
                Log.e("onClick ", "Last Item reached");
            } else {
                isChangedFromClick = true;
                verticalViewPager.setScrollDurationFactor(4);
                verticalViewPager.setCurrentItem(verticalViewPager.getCurrentItem() + 1);
                Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        final int currentBackgroundColor;
                        if (verticalViewPager.getCurrentItem() == verticalIntroItemList.size() - 1) {
                            if (setLastItemBottomViewColor() != null) {
                                currentBackgroundColor = setLastItemBottomViewColor();
                            } else {
                                Log.e(TAG, "Last item bottom view color is null");
                                currentBackgroundColor = verticalIntroItemList.get(0).getColor();
                            }

                        } else {
                            currentBackgroundColor = ContextCompat.getColor(context,
                                    verticalIntroItemList.get(verticalViewPager.getCurrentItem() + 1).getColor());
                        }

                        bottomView.setBackgroundColor(currentBackgroundColor);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0).setDuration(600);
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                verticalViewPager.setScrollDurationFactor(1.5);
                            }
                        });
                        objectAnimator.start();
                    }
                });
            }
        }
    };

    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {
            super.onPageSelected(position);
            if (!isChangedFromClick) {
                if (verticalViewPager.getCurrentItem() == verticalIntroItemList.size()) {
                    Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            int currentBackgroundColor = ContextCompat.getColor(context,
                                    verticalIntroItemList.get(position + 1).getColor());
                            bottomView.setBackgroundColor(currentBackgroundColor);
                            Utils.makeTranslationYAnimation(bottomView, (long) (1000 / (scrollSpeed)));
                        }
                    });
                } else {
                    if (isGoingForward(position)) {
                        Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                final int currentBackgroundColor;
                                if (position == verticalIntroItemList.size() - 1) {
                                    if (setLastItemBottomViewColor() != null) {
                                        currentBackgroundColor = setLastItemBottomViewColor();
                                    } else {
                                        Log.e(TAG, "Last item bottom view color is null");
                                        currentBackgroundColor = verticalIntroItemList.get(0).getColor();
                                    }

                                } else {
                                    currentBackgroundColor = ContextCompat.getColor(context,
                                            verticalIntroItemList.get(position + 1).getColor());
                                }

                                bottomView.setBackgroundColor(currentBackgroundColor);
                                Utils.makeTranslationYAnimation(bottomView, (long) (800 / (scrollSpeed)));
                            }
                        });
                    } else {
                        Utils.makeTranslationYAnimation(bottomView, (long) (600 / (scrollSpeed)), new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                int currentBackgroundColor = ContextCompat.getColor(context,
                                        verticalIntroItemList.get(position + 1).getColor());
                                bottomView.setBackgroundColor(currentBackgroundColor);
                                bottomView.setTranslationY(0);
                            }
                        });
                    }
                }
            } else {
                isChangedFromClick = false;
            }
            currentPosition = position;
        }
    };

    private void addListeners() {
        verticalViewPager.addOnPageChangeListener(pageChangeListener);
        bottomView.setOnClickListener(bottomButtonClickListener);
    }

    private void setUpBottomView() {
        int firstPageBackgroundColor = verticalIntroItemList.get(1).getColor();
        bottomView.setBackgroundColor(ContextCompat.getColor(context, firstPageBackgroundColor));
    }

    private void setUpViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), verticalIntroItemList);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(1.5);
        //verticalViewPager.setPageTransformer(false,new SimplePagerTransform());
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

    private void findViews() {
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

    protected abstract Integer setLastItemBottomViewColor();
}
