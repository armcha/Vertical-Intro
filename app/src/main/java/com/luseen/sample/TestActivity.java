package com.luseen.sample;


import android.util.Log;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TestActivity extends VerticalIntro {

    @Override
    protected void init() {

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorAccent)
                .image(R.drawable.android)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.color2)
                .image(android.R.drawable.bottom_bar)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.android)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.android)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.android)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        setSkipEnabled(true);
        setVibrateEnabled(true);
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.color1;
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
