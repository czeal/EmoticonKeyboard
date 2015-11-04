package com.zeal.android.emoticonsheet.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zeal.android.emoticonsheet.R;
import com.zeal.android.emoticonsheet.provider.IEmoticonMap;

public class EmoticonEditText extends EditText {
    private boolean mUseSystemDefault;
    private int mEmojiSize;
    private int mEmojiAlign;
    private int mTextSize;

    private EmoticonHelper mEmoticonHelper = new EmoticonHelper();

    // 提供编辑框的基本特性
    public EmoticonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmoticonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mUseSystemDefault) {
            super.onTextChanged(text, start, lengthBefore, lengthAfter);
        } else {
            updateText();
        }
    }

    public void updateText() {
        if (mEmoticonHelper != null) {
            mEmoticonHelper.addEmoticon(getContext(), getText(), mEmojiAlign, mEmojiSize, mTextSize);
        }
    }
}
