package com.zeal.android.emoticonsheet.provider;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;

import java.util.List;

public interface IEmoticonWrapper {
    List<Emoticon> getEmoticons();

    int getIcon();
}
