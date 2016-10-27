package com.luseen.verticalintrolibrary;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chatikyan on 19.10.2016.
 */

public class VerticalIntroItem implements Parcelable {

    private Typeface customTypeFace;
    private String title;
    private String text;
    private int image;
    private int backgroundColor;

    private VerticalIntroItem(Builder builder) {
        this.title = builder.title;
        this.text = builder.text;
        this.image = builder.image;
        this.backgroundColor = builder.backgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setCustomTypeFace(Typeface customTypeFace) {
        this.customTypeFace = customTypeFace;
    }

    public Typeface getCustomTypeFace() {
        return customTypeFace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeInt(image);
        dest.writeInt(backgroundColor);
    }

    private VerticalIntroItem(Parcel in) {
        title = in.readString();
        text = in.readString();
        image = in.readInt();
        backgroundColor = in.readInt();
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

    public static class Builder {
        private String title;
        private String text;
        private int image;
        private int backgroundColor;

        public Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder backgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder image(int image) {
            this.image = image;
            return this;
        }

        public VerticalIntroItem build() {
            return new VerticalIntroItem(this);
        }
    }
}
