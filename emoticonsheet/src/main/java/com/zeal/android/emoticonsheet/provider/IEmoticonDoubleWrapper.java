package com.zeal.android.emoticonsheet.provider;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;

import java.util.List;

public interface IEmoticonDoubleWrapper {

    List<Emoticon> getEmoticons(int position);

    // when position is invalid, return 0
    int getIcon(int position);

    int getCount();
}
