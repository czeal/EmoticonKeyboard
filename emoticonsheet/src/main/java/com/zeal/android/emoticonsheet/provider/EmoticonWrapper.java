package com.zeal.android.emoticonsheet.provider;

import android.support.annotation.NonNull;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;

import java.util.List;

public class EmoticonWrapper implements IEmoticonWrapper {
    private int mIcon;
    private List<Emoticon> mEmoticons;

    public EmoticonWrapper(int icon, @NonNull List<Emoticon> emoticons) {
        mIcon = icon;
        mEmoticons = emoticons;
    }

    @Override
    public List<Emoticon> getEmoticons() {
        return mEmoticons;
    }

    @Override
    public int getIcon() {
        return mIcon;
    }

    public void setEmoticons(@NonNull List<Emoticon> mEmoticons) {
        this.mEmoticons = mEmoticons;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}
