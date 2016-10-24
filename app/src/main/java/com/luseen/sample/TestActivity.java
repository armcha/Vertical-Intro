package com.luseen.sample;


import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TestActivity extends VerticalIntro {

    @Override
    protected void init() {
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorAccent, "Space Navigation is a library allowing easily integrate fully customizable Google Spaces like navigation to your app."));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color2, "The PageTransformer interface is a powerful tool."));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimary, "The PageTransformer interface is a powerful tool."));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color3, "I am android developer. \n" +
                "I love android app development. Welcome to my website."));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimary, "Use your awesome developer skills and along with some creativity you can create jaw-dropping animations which make your apps delightful to use"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color2, "Use your awesome developer skills and along with some creativity you can create jaw-dropping animations which make your apps delightful to use"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimaryDark, "AutoLinkTextView is TextView that supports Hashtags (#), Mentions (@) , URLs (http://), Phone and Email automatically detecting and ability to handle clicks."));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimary, "AutoLinkTextView is TextView that supports Hashtags (#), Mentions (@) , URLs (http://), Phone and Email automatically detecting and ability to handle clicks."));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return Color.GREEN;
    }

    @Override
    protected void onSkipPressed(View view) {
        Log.e("onSkipPressed ", "onSkipPressed");
    }

    @Override
    protected void onFragmentChanged(int position) {
        Log.e("onFragmentChanged ", "" + position);
    }
}
