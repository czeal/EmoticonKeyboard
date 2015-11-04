package com.zeal.android.emoticonsheet.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.zeal.android.emoticonsheet.R;
import com.zeal.android.emoticonsheet.provider.IEmoticonMap;

public class EmoticonTextView extends TextView {
    private static final String TAG = "EmoticonTextView";

    private boolean mUseSystemDefault;
    private int mEmojiSize;
    private int mEmojiAlign;
    private int mTextSize;

    private EmoticonHelper mEmoticonHelper = new EmoticonHelper();

    public EmoticonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmoticonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Emoticon);
        mEmojiSize = (int) array.getDimension(R.styleable.Emoticon_emojiSize, getTextSize());
        mEmojiAlign = array.getInt(R.styleable.Emoticon_emojiAlign, DynamicDrawableSpan.ALIGN_BASELINE);
        mUseSystemDefault = array.getBoolean(R.styleable.Emoticon_useSystemDefault, false);
        array.recycle();
        mTextSize = (int) getTextSize();

        setText(getText());
    }

    public void setEmoticonMap(IEmoticonMap emoticonMap) {
        mEmoticonHelper.setEmoticonMap(emoticonMap);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.i(TAG, "do 0");
        if (!mUseSystemDefault && !TextUtils.isEmpty(text)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            Log.i(TAG, "do 1");
            if (mEmoticonHelper != null) {
                Log.i(TAG, "do 2");
                mEmoticonHelper.addEmoticon(getContext(), builder, mEmojiAlign, mEmojiSize, mTextSize);
            }
            text = builder;
        }
        super.setText(text, type);
    }
}
