package com.luseen.verticalintrolibrary;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class SimplePagerTransform implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

        View text = view.findViewById(R.id.text);
        View title = view.findViewById(R.id.title);
        View image = view.findViewById(R.id.image);

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
            text.setAlpha(1.0F - Math.abs(position * 2));
            image.setAlpha(1.0F - Math.abs(position * 2));
            title.setAlpha(1.0F - Math.abs(position * 2));

//            image.setTranslationY(position);
//            title.setTranslationY(position * 2);
//            text.setTranslationY(position / 2);

            float circleWidth = image.getHeight();
            float imageWidth = text.getHeight();

            image.setTranslationY((position * circleWidth * 1.2f));
            //text.setTranslationY((position * circleWidth * 1f));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }


        ////////////////////////


//        //view.setTranslationX(view.getWidth() * -position);
//
//        if (position <= -1.0F || position >= 1.0F) {
//            view.setAlpha(0.0F);
//        } else if (position == 0.0F) {
//            view.setAlpha(1.0F);
//        } else {
//            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
//            //view.setAlpha(1.0F - Math.abs(position));
//
//            image.setTranslationY(position);
//            title.setTranslationY(position * 2);
//            text.setTranslationY(position / 2);
//
////            text.setAlpha(1.0F - Math.abs(position * 2));
////            image.setAlpha(1.0F - Math.abs(position * 2));
////            title.setAlpha(1.0F - Math.abs(position * 2));
//        }

    }

}
