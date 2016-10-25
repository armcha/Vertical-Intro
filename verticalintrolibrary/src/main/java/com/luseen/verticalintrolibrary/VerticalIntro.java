package com.luseen.verticalintrolibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class VerticalIntro extends AppCompatActivity {

    static final String TAG = "VerticalIntro";
    private static final double SCROLL_DURATION_FACTOR_ON_SCROLL = 4;
    private static final double SCROLL_DURATION_FACTOR_ON_CLICK = 1.5;
    private static final double SCROLL_DURATION_FACTOR_ON_SKIP = 0.5f;
    private static final int FORWARD_SCROLL_ANIMATION_DURATION = 800;
    private static final int BACKWARD_SCROLL_ANIMATION_DURATION = 600;
    private static final int DEFAULT_ANIMATION_DURATION = 500;
    private static final int VIBRATE_INTENSITY = 20;

    private List<VerticalIntroItem> verticalIntroItemList = new ArrayList<>();
    private VerticalViewPager verticalViewPager;
    private RelativeLayout skipContainer;
    private RelativeLayout bottomView;
    private TextView nextTextView;
    private Vibrator vibrator;
    private Context context;
    private String nextText;
    private String doneText;
    private boolean isChangedFromClick = false;
    private boolean isVibrateEnabled = true;
    private boolean isSkipEnabled = true;
    private float startYPoint, endYPoint;
    private int currentPosition;
    private double scrollSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_intro_activity);
        context = this;

        init();

        findAndSetupViews();

        setUpViewPager();

        setUpSettings();

        setUpBottomView();

        getScrollSpeed();

        addListeners();
    }

    private void setUpSettings() {
        if (isSkipEnabled) {
            skipContainer.setVisibility(View.VISIBLE);
        } else {
            skipContainer.setVisibility(View.GONE);
        }
    }

    private View.OnTouchListener bottomButtonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    startYPoint = motionEvent.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    endYPoint = motionEvent.getY();

                    if (startYPoint > endYPoint) {
                        bottomView.performClick();
                    }
                    break;
                }
            }
            return false;
        }
    };

    private View.OnClickListener bottomButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isVibrateEnabled) {
                vibrator.vibrate(VIBRATE_INTENSITY);
            }
            int currentViewPagerItemPosition = verticalViewPager.getCurrentItem();
            boolean isLastPosition = currentViewPagerItemPosition == verticalIntroItemList.size() - 1;
            if (isLastPosition) {
                onDonePressed();
                //onFragmentChanged(currentViewPagerItemPosition);
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

    private View.OnClickListener skipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isVibrateEnabled) {
                vibrator.vibrate(VIBRATE_INTENSITY);
            }
            int lastItem = verticalIntroItemList.size();
            verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_SKIP);
            verticalViewPager.setCurrentItem(lastItem);
            onSkipPressed(view);
        }
    };

    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {
            super.onPageSelected(position);
            onFragmentChanged(position);

            boolean isLastPosition = position == verticalIntroItemList.size() - 1;
            if (isSkipEnabled) {
                if (isLastPosition) {
                    Utils.changeTextWhitFade(nextTextView, doneText);
                    Utils.changeViewVisibilityWhitFade(skipContainer, false);
                    verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_CLICK);
                } else {
                    if (nextTextView.getText().toString().equals(doneText))
                        Utils.changeTextWhitFade(nextTextView, nextText);
                    Utils.changeViewVisibilityWhitFade(skipContainer, true);
                }
            }

            if (!isChangedFromClick) {
                if (isGoingForward(position)) {
                    Utils.makeTranslationYAnimation(bottomView, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            changeBottomViewBackgroundColor();

                            long duration;
                            if (scrollSpeed == 0) {
                                duration = DEFAULT_ANIMATION_DURATION;
                            } else {
                                duration = (long) (FORWARD_SCROLL_ANIMATION_DURATION / (scrollSpeed));
                            }
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
                                    verticalIntroItemList.get(position + 1).getBackgroundColor());
                            bottomView.setBackgroundColor(currentBackgroundColor);
                            bottomView.setTranslationY(0);
                        }
                    });
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
                currentBackgroundColor = verticalIntroItemList.get(0).getBackgroundColor();
            }
        } else {
            currentBackgroundColor = verticalIntroItemList.get(verticalViewPager.getCurrentItem() + 1).getBackgroundColor();
        }
        bottomView.setBackgroundColor(ContextCompat.getColor(context, currentBackgroundColor));
    }

    private void addListeners() {
        verticalViewPager.addOnPageChangeListener(pageChangeListener);
        bottomView.setOnClickListener(bottomButtonClickListener);
        bottomView.setOnTouchListener(bottomButtonOnTouchListener);
    }

    private void setUpBottomView() {
        int firstPageBackgroundColor;
        if (verticalIntroItemList.size() > 1) {
            firstPageBackgroundColor = verticalIntroItemList.get(1).getBackgroundColor();
        } else if (setLastItemBottomViewColor() != null) {
            firstPageBackgroundColor = setLastItemBottomViewColor();
        } else {
            firstPageBackgroundColor = verticalIntroItemList.get(0).getBackgroundColor();
        }
        bottomView.setBackgroundColor(ContextCompat.getColor(context, firstPageBackgroundColor));
    }

    private void setUpViewPager() {
        VerticalIntroPagerAdapter pagerAdapter =
                new VerticalIntroPagerAdapter(getSupportFragmentManager(), verticalIntroItemList);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_CLICK);
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

    private void findAndSetupViews() {
        if (verticalIntroItemList.size() == 0) {
            throw new NullPointerException(TAG + " You need at least one item");
        }

        if (verticalIntroItemList.size() == 1) {
            skipContainer.setVisibility(View.GONE);
        }
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        skipContainer = (RelativeLayout) findViewById(R.id.skip_container);
        nextTextView = (TextView) findViewById(R.id.next);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        skipContainer.setOnClickListener(skipClickListener);

        nextText = getString(R.string.next);
        doneText = getString(R.string.done);

        int lastFragmentColor = verticalIntroItemList.get(verticalIntroItemList.size() - 1).getBackgroundColor();
        verticalViewPager.setBackgroundColor(ContextCompat.getColor(this, lastFragmentColor));
        int skipButtonTopMargin = Utils.getStatusBarHeight(this);
        ((RelativeLayout.LayoutParams) skipContainer.getLayoutParams()).setMargins(0, skipButtonTopMargin, 0, 0);
    }

    private boolean isGoingForward(int position) {
        return currentPosition < position;
    }

    protected abstract void init();

    protected abstract Integer setLastItemBottomViewColor();

    protected abstract void onSkipPressed(View view);

    protected abstract void onFragmentChanged(int position);

    protected abstract void onDonePressed();

    protected void addIntroItem(VerticalIntroItem verticalIntroItem) {
        verticalIntroItemList.add(verticalIntroItem);
    }

    protected void setSkipEnabled(boolean skipEnabled) {
        isSkipEnabled = skipEnabled;
    }

    protected void setVibrateEnabled(boolean vibrateEnabled) {
        isVibrateEnabled = vibrateEnabled;
    }
}
