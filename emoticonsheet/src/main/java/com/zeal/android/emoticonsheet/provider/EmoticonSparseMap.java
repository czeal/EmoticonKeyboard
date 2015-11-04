package com.zeal.android.emoticonsheet.provider;

import android.content.Context;
import android.util.SparseIntArray;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;

public class EmoticonSparseMap implements IEmoticonMap {
    private static EmoticonSparseMap sEmoticonSparseMap;
    private Context mContext;

    private IEmoticonDoubleWrapper mDoubleWrapper;
    private SparseIntArray mMap;

    private EmoticonSparseMap(Context context) {
        mContext = context;
        initEmotions();
    }

    public static EmoticonSparseMap get(Context context) {
        if (sEmoticonSparseMap == null) {
            sEmoticonSparseMap = new EmoticonSparseMap(context.getApplicationContext());
        }

        return sEmoticonSparseMap;
    }

    public void initEmotions() {
        mDoubleWrapper = EmoticonProvider.get(mContext).getDoubleWrapper();

        mMap = new SparseIntArray();
        for (int i = 0; i < mDoubleWrapper.getCount(); i++) {
            for (Emoticon emoticon : mDoubleWrapper.getEmoticons(i)) {
                mMap.put(emoticon.getValue(), emoticon.getIcon());
            }
        }
    }

    @Override
    public int getIcon(int key) {
        return mMap.get(key);
    }
}
