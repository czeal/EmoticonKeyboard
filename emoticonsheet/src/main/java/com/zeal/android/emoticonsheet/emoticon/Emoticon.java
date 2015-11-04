package com.zeal.android.emoticonsheet.emoticon;

import android.os.Parcel;
import android.os.Parcelable;

public class Emoticon implements IEmoticon {
    private int value;
    private int icon;

    public static final Creator<Emoticon> CREATOR = new Creator<Emoticon>() {
        @Override
        public Emoticon createFromParcel(Parcel source) {
            return new Emoticon(source);
        }

        @Override
        public Emoticon[] newArray(int size) {
            return new Emoticon[size];
        }
    };

    public Emoticon(Parcel in) {
        value = in.readInt();
        icon = in.readInt();
    }

    public Emoticon() {

    }

    public Emoticon(int value, int icon) {
        this.value = value;
        this.icon = icon;
    }

    @Override
    public String getContent() {
        return new String(Character.toChars(value));
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeInt(icon);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Emoticon && value == ((Emoticon) o).value;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }
}
