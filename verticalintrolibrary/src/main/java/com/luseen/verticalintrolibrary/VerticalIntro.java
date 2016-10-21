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

    private static final String TAG = "VerticalIntro";
    private static final double SCROLL_DURATION_FACTOR_ON_SCROLL = 4;
    private static final double SCROLL_DURATION_FACTOR_ON_CLICK = 1.5;
    private static final int FORWARD_SCROLL_ANIMATION_DURATION = 800;
    private static final int BACKWARD_SCROLL_ANIMATION_DURATION = 600;

    private List<VerticalIntroItem> verticalIntroItemList = new ArrayList<>();
    private VerticalViewPager verticalViewPager;
    private RelativeLayout bottomView;
    private Context context;
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
            int currentViewPagerItemPosition = verticalViewPager.getCurrentItem();
            if (currentViewPagerItemPosition == verticalIntroItemList.size() - 1) {
                Log.e("onClick ", "Last Item reached");
            } else {
                isChangedFromClick = true;
                verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_SCROLL);
                verticalViewPager.setCurrentItem(currentViewPagerItemPosition + 1);
                Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        changeBottomViewBackgroundColor();

                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bottomView, View.TRANSLATION_Y, 0);
                        objectAnimator.setDuration(BACKWARD_SCROLL_ANIMATION_DURATION);
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_CLICK);
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
                            long duration = (long) (FORWARD_SCROLL_ANIMATION_DURATION / (scrollSpeed));
                            Utils.makeTranslationYAnimation(bottomView, duration);
                        }
                    });
                } else {
                    if (isGoingForward(position)) {
                        Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                changeBottomViewBackgroundColor();

                                long duration = (long) (FORWARD_SCROLL_ANIMATION_DURATION / (scrollSpeed));
                                Utils.makeTranslationYAnimation(bottomView, duration);
                            }
                        });
                    } else {
                        long duration = (long) (BACKWARD_SCROLL_ANIMATION_DURATION / (scrollSpeed));
                        Utils.makeTranslationYAnimation(bottomView, duration, new AnimatorListenerAdapter() {
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

    private void changeBottomViewBackgroundColor() {
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
    }

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
        verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_CLICK);
        //verticalViewPager.setPageTransformer(false,new SimplePagerTransform());
    }

    private void getScrollSpeed() {
        final double[] newX = new double[1];
        final double[] newY = new double[1];
        final double[] oldX = new double[1];
        final double[] oldY = new double[1];
        final long[] scrollStartTime = new long[1];
        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    oldX[0] = event.getX();
                    oldY[0] = event.getY();
                    scrollStartTime[0] = System.currentTimeMillis();
                } else if (action == MotionEvent.ACTION_UP) {
                    long scrollTime = System.currentTimeMillis() - scrollStartTime[0];
                    newX[0] = event.getX();
                    newY[0] = event.getY();
                    double distance = Math.sqrt((newX[0] - oldX[0]) * (newX[0] - oldX[0])
                            + (newY[0] - oldY[0]) * (newY[0] - oldY[0]));
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
