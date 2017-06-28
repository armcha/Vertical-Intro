package com.luseen.verticalintrolibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
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

    private static final int LANDSCAPE_MODE_DEFAULT_ANIMATION_DURATION = 50;
    private static final int BACKWARD_SCROLL_ANIMATION_DURATION = 600;
    private static final int FORWARD_SCROLL_ANIMATION_DURATION = 800;
    private static final int DEFAULT_ANIMATION_DURATION = 400;
    private static final double SCROLL_DURATION_FACTOR_ON_CLICK = 1.5;
    private static final double SCROLL_DURATION_FACTOR_ON_SKIP = 0.5f;
    private static final double SCROLL_DURATION_FACTOR_ON_SCROLL = 4;

    private List<VerticalIntroItem> verticalIntroItemList = new ArrayList<>();
    private VerticalViewPager verticalViewPager;
    private RelativeLayout skipContainer;
    private RelativeLayout bottomView;
    private Typeface customTypeFace;
    private TextView skipTextView;
    private TextView nextTextView;
    private Vibrator vibrator;
    private Context context;
    private String nextText;
    private String doneText;
    private String skipText;

    private boolean isChangedFromClick = false;
    private boolean isVibrateEnabled = true;
    private boolean isSkipEnabled = true;
    private int vibrateIntensity = 20;
    private int currentPosition;
    private int skipColor;
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

    private View.OnClickListener bottomButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isVibrateEnabled) {
                vibrator.vibrate(vibrateIntensity);
            }
            int currentViewPagerItemPosition = verticalViewPager.getCurrentItem();
            boolean isLastPosition = currentViewPagerItemPosition == verticalIntroItemList.size() - 1;
            if (isLastPosition) {
                onDonePressed();
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
                        objectAnimator.setDuration(DEFAULT_ANIMATION_DURATION);
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
                vibrator.vibrate(vibrateIntensity);
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

            Utils.setUpRecentAppStyle(VerticalIntro.this, verticalIntroItemList.get(position).getBackgroundColor());
            // TODO: 28.06.2017  fix animation
            nextTextView.setTextColor(ContextCompat.getColor(context, verticalIntroItemList.get(position).getNextTextColor()));

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
                                if (duration > DEFAULT_ANIMATION_DURATION)
                                    duration = DEFAULT_ANIMATION_DURATION;
                            }
                            Utils.makeTranslationYAnimation(bottomView, duration);
                        }
                    });
                } else {
                    long duration = (long) (BACKWARD_SCROLL_ANIMATION_DURATION / (scrollSpeed));
                    if (duration > DEFAULT_ANIMATION_DURATION)
                        duration = DEFAULT_ANIMATION_DURATION;

                    if (isInLandscapeMode() && duration < 500) {
                        duration = LANDSCAPE_MODE_DEFAULT_ANIMATION_DURATION;
                    }
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

    public boolean isInLandscapeMode() {
        int value = getResources().getConfiguration().orientation;
        return value == Configuration.ORIENTATION_LANDSCAPE;
    }

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
        if (hasCustomFont()) {
            for (VerticalIntroItem verticalIntroItem : verticalIntroItemList) {
                verticalIntroItem.setCustomTypeFace(customTypeFace);
            }
        }
        VerticalIntroPagerAdapter pagerAdapter =
                new VerticalIntroPagerAdapter(getSupportFragmentManager(), verticalIntroItemList);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.setScrollDurationFactor(SCROLL_DURATION_FACTOR_ON_CLICK);
    }

    @SuppressLint("ClickableViewAccessibility")
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

        skipContainer = (RelativeLayout) findViewById(R.id.skip_container);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        skipTextView = (TextView) findViewById(R.id.skip);
        nextTextView = (TextView) findViewById(R.id.next);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        skipContainer.setOnClickListener(skipClickListener);

        if (verticalIntroItemList.size() == 1) {
            skipContainer.setVisibility(View.GONE);
        }

        if (nextText == null)
            nextText = getString(R.string.next);
        if (doneText == null)
            doneText = getString(R.string.done);
        if (skipText == null)
            skipText = getString(R.string.skip);
        if (skipColor == 0) {
            skipColor = R.color.white;
        }

        skipTextView.setText(skipText);
        nextTextView.setText(nextText);
        skipTextView.setTextColor(ContextCompat.getColor(this, skipColor));
        nextTextView.setTextColor(ContextCompat.getColor(this, verticalIntroItemList.get(0).getNextTextColor()));

        Utils.setUpRecentAppStyle(this, verticalIntroItemList.get(0).getBackgroundColor());

        if (hasCustomFont()) {
            nextTextView.setTypeface(customTypeFace);
            skipTextView.setTypeface(customTypeFace);
        }

        int lastFragmentColor = verticalIntroItemList.get(verticalIntroItemList.size() - 1).getBackgroundColor();
        verticalViewPager.setBackgroundColor(ContextCompat.getColor(this, lastFragmentColor));
        int skipButtonTopMargin = Utils.getStatusBarHeight(this);
        ((RelativeLayout.LayoutParams) skipContainer.getLayoutParams()).setMargins(0, skipButtonTopMargin, 0, 0);
    }

    private boolean isGoingForward(int position) {
        return currentPosition < position;
    }

    private boolean hasCustomFont() {
        return customTypeFace != null;
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

    public void setSkipColor(int skipColor) {
        this.skipColor = skipColor;
    }

    protected void setVibrateEnabled(boolean vibrateEnabled) {
        isVibrateEnabled = vibrateEnabled;
    }

    protected void setCustomTypeFace(Typeface customTypeFace) {
        this.customTypeFace = customTypeFace;
    }

    public void setNextText(String nextText) {
        this.nextText = nextText;
    }

    public void setDoneText(String doneText) {
        this.doneText = doneText;
    }

    public void setSkipText(String skipText) {
        this.skipText = skipText;
    }

    //default is 20
    public void setVibrateIntensity(int vibrateIntensity) {
        this.vibrateIntensity = vibrateIntensity;
    }
}
