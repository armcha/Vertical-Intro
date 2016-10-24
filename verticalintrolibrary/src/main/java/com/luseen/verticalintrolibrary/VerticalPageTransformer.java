package com.luseen.verticalintrolibrary;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class VerticalPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

        View text = view.findViewById(R.id.text);
        View title = view.findViewById(R.id.title);
        View image = view.findViewById(R.id.image);

        if (position < -1) {
            view.setAlpha(0);

        } else if (position <= 1) {
            view.setAlpha(1);
            view.setTranslationX(view.getWidth() * -position);

            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

            text.setAlpha(1.0F - Math.abs(position * 2));
            image.setAlpha(1.0F - Math.abs(position * 2));
            title.setAlpha(1.0F - Math.abs(position * 2));

            float imageHeight = image.getHeight();

            image.setTranslationY((position * imageHeight * 1.2f));
        } else {
            view.setAlpha(0);
        }
    }

}
