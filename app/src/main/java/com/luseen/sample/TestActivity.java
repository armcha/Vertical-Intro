package com.luseen.sample;


import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TestActivity extends VerticalIntro {

    @Override
    protected void init() {

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.white)
                .image(R.drawable.first)
                .title("Lorem Ipsum Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .setTextColor("#0f9595")
                .setTitleColor("#0f9595")
                .setTextSize(14)
                .setTitleSize(17)
                .setNextTextColor("#0f9595")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.white)
                .image(R.drawable.second)
                .title("Lorem Ipsum Lorem Ipsum ")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .setTextColor("#0f9595")
                .setTitleColor("#0f9595")
                .setTextSize(14)
                .setTitleSize(17)
                .setNextTextColor("#0f9595")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.white)
                .image(R.drawable.third)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .setTextColor("#0f9595")
                .setTitleColor("#0f9595")
                .setTextSize(14)
                .setTitleSize(17)
                .setNextTextColor("#0f9595")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.white)
                .image(R.drawable.fourth)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .setTextColor("#0f9595")
                .setTitleColor("#0f9595")
                .setTextSize(14)
                .setTitleSize(17)
                .setNextTextColor("#0f9595")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.white)
                .image(R.drawable.fifth)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .setTextColor("#0f9595")
                .setTitleColor("#0f9595")
                .setTextSize(14)
                .setTitleSize(17)
                .setNextTextColor("#0f9595")
                .build());

        setSkipEnabled(true);
        setVibrateEnabled(true);
//        setNextText("OK");
//        setDoneText("FINISH HIM");
//        setSkipText("GO GO");
        setVibrateIntensity(20);
        setCustomTypeFace(Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf"));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.color2;
    }

    @Override
    protected void onSkipPressed(View view) {
        Log.e("onSkipPressed ", "onSkipPressed");
    }

    @Override
    protected void onFragmentChanged(int position) {
        Log.e("onFragmentChanged ", "" + position);
    }

    @Override
    protected void onDonePressed() {
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }
}
