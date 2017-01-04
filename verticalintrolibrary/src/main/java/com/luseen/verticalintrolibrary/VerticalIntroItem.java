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
    private String textColor;
    private String titleColor;
    private float textSize;
    private float titleSize;
    private String nextTextColor;

    public String getNextTextColor() {
        return nextTextColor;
    }

    public void setNextTextColor(String nextTextColor) {
        this.nextTextColor = nextTextColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
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
        this.textSize=builder.textSize;
        this.textColor=builder.textColor;
        this.titleSize=builder.titleSize;
        this.titleColor=builder.titleColor;
        this.nextTextColor=builder.nextTextColor;
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
        private String textColor;
        private String titleColor;
        private float textSize;
        private float titleSize;
        private String nextTextColor;

        public Builder setNextTextColor(String nextTextColor) {
            this.nextTextColor = nextTextColor;
            return this;
        }




        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }


        public Builder setTitleColor(String titleColor) {
            this.titleColor = titleColor;
            return this;
        }


        public Builder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }


        public Builder setTitleSize(float titleSize) {
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
