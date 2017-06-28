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
    private int backgroundColor;
    private int nextTextColor;
    private int titleColor;
    private int textColor;
    private int image;
    private float textSize;
    private float titleSize;

    protected VerticalIntroItem(Parcel in) {
        title = in.readString();
        text = in.readString();
        image = in.readInt();
        backgroundColor = in.readInt();
        textColor = in.readInt();
        titleColor = in.readInt();
        textSize = in.readFloat();
        titleSize = in.readFloat();
        nextTextColor = in.readInt();
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

    public int getNextTextColor() {
        return nextTextColor;
    }

    public void setNextTextColor(int nextTextColor) {
        this.nextTextColor = nextTextColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    private VerticalIntroItem(Builder builder) {
        this.title = builder.title;
        this.text = builder.text;
        this.image = builder.image;
        this.backgroundColor = builder.backgroundColor;
        this.textSize = builder.textSize;
        this.textColor = builder.textColor;
        this.titleSize = builder.titleSize;
        this.titleColor = builder.titleColor;
        this.nextTextColor = builder.nextTextColor;
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
        dest.writeInt(textColor);
        dest.writeInt(titleColor);
        dest.writeFloat(textSize);
        dest.writeFloat(titleSize);
        dest.writeInt(nextTextColor);
    }

    public static class Builder {

        private String title;
        private String text;
        private int nextTextColor = R.color.white;
        private int titleColor = R.color.white;
        private int textColor = R.color.white;
        private int backgroundColor;
        private int image;
        private float titleSize;
        private float textSize;

        public Builder nextTextColor(int nextTextColor) {
            this.nextTextColor = nextTextColor;
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder titleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder textSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder titleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

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
