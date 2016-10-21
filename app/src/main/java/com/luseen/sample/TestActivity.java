package com.luseen.sample;


import android.graphics.Color;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TestActivity extends VerticalIntro {

    @Override
    protected void init() {
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorAccent, "First"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color2, "Second"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color3, "Third"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimary, "Fourth"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimaryDark, "Fifth"));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return Color.CYAN;
    }
}
