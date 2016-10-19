package com.luseen.verticalIntro;

import android.support.annotation.IntegerRes;

/**
 * Created by Chatikyan on 19.10.2016.
 */

public class VerticalIntroItem {

    private String text;
    @IntegerRes
    private int image;
    @IntegerRes
    private int color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
