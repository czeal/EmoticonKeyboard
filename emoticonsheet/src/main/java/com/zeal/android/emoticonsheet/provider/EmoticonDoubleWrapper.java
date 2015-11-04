package com.zeal.android.emoticonsheet.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;

import java.util.List;

public class EmoticonDoubleWrapper implements IEmoticonDoubleWrapper {
    private List<IEmoticonWrapper> mWrappers;

    public EmoticonDoubleWrapper(@NonNull List<IEmoticonWrapper> wrappers) {
        mWrappers = wrappers;
    }

    @Override
    @Nullable
    public List<Emoticon> getEmoticons(int position) {
        if (position < mWrappers.size()) {
            return mWrappers.get(position).getEmoticons();
        } else {
            return null;
        }
    }

    @Override
    public int getIcon(int position) {
        if (position < mWrappers.size()) {
            return mWrappers.get(position).getIcon();
        } else {
            return 0;
        }
    }

    @Override
    public int getCount() {
        return mWrappers.size();
    }

    public void setEmoticonWrapper(@NonNull List<IEmoticonWrapper> wrappers) {
        mWrappers = wrappers;
    }
}
