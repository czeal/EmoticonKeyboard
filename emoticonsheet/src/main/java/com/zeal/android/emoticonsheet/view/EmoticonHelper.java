package com.zeal.android.emoticonsheet.view;

import android.content.Context;
import android.text.Spannable;
import android.util.Log;

import com.zeal.android.emoticonsheet.provider.IEmoticonMap;

public class EmoticonHelper {
    private static final String TAG = "EmoticonHelper";

    private IEmoticonMap mEmoticonMap;

    public void setEmoticonMap(IEmoticonMap emoticonMap) {
        mEmoticonMap = emoticonMap;
    }

    public void addEmoticon(Context context, Spannable text, int emojiAlign, int emojiSize, int textSize) {
        if (mEmoticonMap == null) {
            return;
        }

        Log.i(TAG, "do 2");
        EmoticonSpan[] oldSpans = text.getSpans(0, text.length(), EmoticonSpan.class);
        for (EmoticonSpan emojiSpan : oldSpans) {
            text.removeSpan(emojiSpan);
        }

        // 1f___
        // 1f___ 1f1f_
        // 00__
        // 00__ 20e3
        // 2___
        // 3___
        int skip;
        int textLength = text.length();
        for (int i = 0; i < textLength; i += skip) {
            int icon = 0;
            int unicode = Character.codePointAt(text, i);
            skip = Character.charCount(unicode);

            // 优先按最短匹配处理
            if (unicode > 0xff) {
                // 假定是 1f___
                icon = mEmoticonMap.getIcon(unicode);
            }

            // 不是 1f___
            // TODO

            if (icon > 0) {
                text.setSpan(new EmoticonSpan(context, icon, emojiAlign, emojiSize, textSize),
                        i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
