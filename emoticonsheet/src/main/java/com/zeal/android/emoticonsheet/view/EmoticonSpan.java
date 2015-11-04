package com.zeal.android.emoticonsheet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import java.lang.ref.WeakReference;

public class EmoticonSpan extends DynamicDrawableSpan {

    private Context mContext;
    private Drawable mDrawable;
    private WeakReference<Drawable> mDrawableRef;

    private final int mResourceId;
    private final int mEmojiAlign;
    private final int mEmojiSize;
    private final int mTextSize;

    public EmoticonSpan(Context context, int resId, int emojiAlign, int emojiSize, int textSize) {
        super(emojiAlign);
        mContext = context;
        mResourceId = resId;
        mEmojiAlign = emojiAlign;
        mEmojiSize = emojiSize;
        mTextSize = textSize;
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable == null) {
            mDrawable = mContext.getResources().getDrawable(mResourceId);
            int top = (mTextSize - mEmojiSize) / 2;
            if (mDrawable != null) {
                mDrawable.setBounds(0, top, mEmojiSize, top + mEmojiSize);
            }
        }

        return mDrawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getCachedDrawable();
        canvas.save();

        // set the default
        int transY = bottom - drawable.getBounds().bottom;
        if (mEmojiAlign == ALIGN_BASELINE) {
            Rect bounds = drawable.getBounds();
            transY = top - bounds.top + (bottom - top) / 2 - (bounds.bottom - bounds.top) / 2;
        }

        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        if (mDrawableRef == null || mDrawableRef.get() == null) {
            mDrawableRef = new WeakReference<>(getDrawable());
        }
        return mDrawableRef.get();
    }
}
