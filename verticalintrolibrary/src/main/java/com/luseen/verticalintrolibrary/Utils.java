package com.luseen.verticalintrolibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Chatikyan on 20.10.2016.
 */

class Utils {

    static void makeTranslationYAnimation(View view, AnimatorListenerAdapter listenerAdapter) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getHeight());
        //objectAnimator.setStartDelay(100);
        objectAnimator.addListener(listenerAdapter);
        objectAnimator.setDuration(100);
        objectAnimator.start();
    }

    static void makeTranslationYAnimation(View view, long duration, AnimatorListenerAdapter listenerAdapter) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getHeight());
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(listenerAdapter);
        objectAnimator.start();
    }

    static void makeTranslationYAnimation(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    static int getStatusBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    static void changeViewVisibilityWhitFade(final View view, final boolean reverse) {
        float alphaValue;
        if (reverse) {
            view.setVisibility(View.VISIBLE);
            alphaValue = 1f;
        } else {
            alphaValue = 0f;
        }
        view.animate()
                .alpha(alphaValue)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (reverse)
                            view.setVisibility(View.VISIBLE);
                        else
                            view.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    static void changeTextWhitFade(final TextView textView, final String text) {
        textView.animate()
                .setDuration(200)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        textView.setAlpha(0);
                        textView.setText(text);
                        textView.animate()
                                .setDuration(200)
                                .alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        textView.setAlpha(1);
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }
}
