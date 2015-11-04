package com.zeal.android.emoticonsheet.emoticon;

import android.os.Parcelable;

public interface IEmoticon extends Parcelable {
    String getContent();

    int getIcon();

    int getValue(); // use for check
}
