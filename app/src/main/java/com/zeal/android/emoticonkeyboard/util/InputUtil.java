package com.zeal.android.emoticonkeyboard.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputUtil {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (manager.isActive() && view != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (manager.isActive() && view != null) {
            manager.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.RESULT_SHOWN);
        }
    }

    public static void changeKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (manager.isActive() && view != null) {
            manager.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.RESULT_SHOWN);
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
