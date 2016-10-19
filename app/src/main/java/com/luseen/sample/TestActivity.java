package com.luseen.sample;


import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TestActivity extends VerticalIntro {

    @Override
    protected void init() {
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorAccent, "A"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color2, "A"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.color3, "A"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimary, "A"));
        addVerticalIntroItem(new VerticalIntroItem(1, R.color.colorPrimaryDark, "A"));
    }
}
