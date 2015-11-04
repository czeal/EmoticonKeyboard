package com.zeal.android.emoticonsheet.provider;

import android.content.Context;

import com.zeal.android.emoticonsheet.emoticon.OtherEmoticon;
import com.zeal.android.emoticonsheet.emoticon.PeopleEmoticon;

import java.util.ArrayList;
import java.util.List;

public class EmoticonProvider {
    private static EmoticonProvider sEmoticonProvider;
    private Context mContext;

    private IEmoticonDoubleWrapper mDoubleWrapper;

    private EmoticonProvider(Context context) {
        mContext = context;
        initEmotions();
    }

    public static EmoticonProvider get(Context context) {
        if (sEmoticonProvider == null) {
            sEmoticonProvider = new EmoticonProvider(context.getApplicationContext());
        }

        return sEmoticonProvider;
    }

    private void initEmotions() {
        List<IEmoticonWrapper> wrappers = new ArrayList<>();
        EmoticonWrapper wrapper;
        wrapper = new EmoticonWrapper(PeopleEmoticon.getIcon(), PeopleEmoticon.getEmoticons());
        wrappers.add(wrapper);
        wrapper = new EmoticonWrapper(OtherEmoticon.getIcon(), OtherEmoticon.getEmoticons());
        wrappers.add(wrapper);

        mDoubleWrapper = new EmoticonDoubleWrapper(wrappers);
    }

    public IEmoticonDoubleWrapper getDoubleWrapper() {
        return mDoubleWrapper;
    }
}
