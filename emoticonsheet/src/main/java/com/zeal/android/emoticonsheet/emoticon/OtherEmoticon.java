package com.zeal.android.emoticonsheet.emoticon;

import com.zeal.android.emoticonsheet.R;

import java.util.ArrayList;
import java.util.List;

public class OtherEmoticon {
    private static final int icon;
    private static final List<Emoticon> mEmoticons;

    static {
        icon = R.drawable.emoticon_characters;

        mEmoticons = new ArrayList<>();
        mEmoticons.add(new Emoticon(0x00a9, R.drawable.emoji_00a9));
        mEmoticons.add(new Emoticon(0x00ae, R.drawable.emoji_00ae));
        mEmoticons.add(new Emoticon(0x1f0cf, R.drawable.emoji_1f0cf));
    }

    public static List<Emoticon> getEmoticons() {
        return mEmoticons;
    }

    public static int getIcon() {
        return icon;
    }
}
