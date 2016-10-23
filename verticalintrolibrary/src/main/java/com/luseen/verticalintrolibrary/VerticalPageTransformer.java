package com.luseen.verticalintrolibrary;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Chatikyan on 18.10.2016.
 */

public class VerticalPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            view.setAlpha(1);

            // Counteract the default slide transition
            view.setTranslationX(view.getWidth() * -position);

            //set Y position to swipe in from top
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

}
