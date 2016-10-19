package com.luseen.verticalintrolibrary;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;

/**
 * Created by Chatikyan on 19.10.2016.
 */

public class VerticalIntroItem implements Parcelable {

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

    public VerticalIntroItem(int image, int color, String text) {
        this.image = image;
        this.color = color;
        this.text = text;
    }

    private VerticalIntroItem(Parcel in) {
        text = in.readString();
        image = in.readInt();
        color = in.readInt();
    }

    public static final Creator<VerticalIntroItem> CREATOR = new Creator<VerticalIntroItem>() {
        @Override
        public VerticalIntroItem createFromParcel(Parcel in) {
            return new VerticalIntroItem(in);
        }

        @Override
        public VerticalIntroItem[] newArray(int size) {
            return new VerticalIntroItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(image);
        dest.writeInt(color);
    }
}
