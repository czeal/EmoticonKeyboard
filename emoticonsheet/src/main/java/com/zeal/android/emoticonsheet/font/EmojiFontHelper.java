package com.zeal.android.emoticonsheet.font;

import android.content.Context;
import android.graphics.Typeface;

public class EmojiFontHelper {
    public static final String ROOT = "fonts/";
//    public static final String EMOJI_ONE = "EmojiOneFont.ttf";
//    public static final String TWITTER_EMOJI = "TwitterEmoji.ttf";
//    public static final String WEB_FONT = "fontawesome-webfont.ttf";
//    public static final String APPLE_EMOJI = "AppleColorEmoji.ttf";
//    public static final String MAC_EMOJI = "Mac.ttf";
    public static final String APPLE_SMALL = "Apple-Small.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), ROOT + font);
    }
}
