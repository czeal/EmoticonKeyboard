package com.zeal.android.emoticonkeyboard;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zeal.android.emoticonkeyboard.util.InputUtil;
import com.zeal.android.emoticonsheet.event.IEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.event.OnEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.layout.EmoticonSheetLayout;
import com.zeal.android.emoticonsheet.provider.EmoticonProvider;
import com.zeal.android.emoticonsheet.provider.EmoticonSparseMap;
import com.zeal.android.emoticonsheet.view.EmoticonEditText;
import com.zeal.android.emoticonsheet.view.EmoticonTextView;

/**
 * use adb shell am start -n com.zeal.android.emoticonkeyboard/com.zeal.android.emoticonkeyboard.NewActivity
 * to see.
 */
public class NewActivity extends AppCompatActivity implements IEmoticonSheetLayoutListener {
    private EmoticonEditText mEmojiEditText;
//    private EmoticonTextView mEmojiTextView;

    private ImageView mPickEmojis;

    private View mEmoticonSheetLayoutDivideView;
    private EmoticonSheetLayout mEmoticonSheetLayout;
    private OnEmoticonSheetLayoutListener mListener;
    private AdjustEmoticonSheet mAdjust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        mEmojiEditText = (EmoticonEditText) findViewById(R.id.emoji_edit_text);
//        mEmojiTextView = (EmoticonTextView) findViewById(R.id.emoji_text_view);
        mPickEmojis = (ImageView) findViewById(R.id.action_pick_emojis);

        mAdjust = new AdjustEmoticonSheet();

        mEmoticonSheetLayoutDivideView = findViewById(R.id.emoticon_sheet_layout_divide_view);
        mEmoticonSheetLayout = (EmoticonSheetLayout) findViewById(R.id.emoticon_sheet_layout);
        setAdapter();

        mEmojiEditText.setEmoticonMap(EmoticonSparseMap.get(this));
        //mEmojiEditText.setTypeface(EmojiFontHelper.getTypeface(getContext(), EmojiFontHelper.APPLE_SMALL));
//        mEmojiTextView.setEmoticonMap(EmoticonSparseMap.get(this));

//        mEmojiEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mEmojiTextView.setText(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        mEmojiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Test", "OK");
                mAdjust.setInputState(AdjustEmoticonSheet.INPUT_STATE_ONLY_KEY_BOARD);
            }
        });
        mEmojiEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAdjust.hideEmojiSheet();
                return false;
            }
        });

        mPickEmojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdjust.exchange();
//                if (mAdjust.isEmoticonSheetLayoutShow()) {
//                    mAdjust.setInputState(AdjustEmoticonSheet.INPUT_STATE_ONLY_KEY_BOARD);
//                } else {
//                    mAdjust.setInputState(AdjustEmoticonSheet.INPUT_STATE_ONLY_EMOJI_SHEET);
//                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdjust.isEmoticonSheetLayoutShow()) {
            Log.i("NewActivity", "only emoticon sheet");
            mAdjust.setInputState(AdjustEmoticonSheet.INPUT_STATE_ONLY_EMOJI_SHEET);
        }
    }

    public void setAdapter() {
        EmoticonProvider provider = EmoticonProvider.get(this);
        mEmoticonSheetLayout.setAdapter(getSupportFragmentManager(), provider.getDoubleWrapper());
    }

    @Override
    public OnEmoticonSheetLayoutListener getOnEmoticonSheetLayoutListener() {
        if (mListener == null) {
            mListener = new OnEmoticonSheetLayoutListener() {
                @Override
                public void onEmoticonClickListener(String content) {
                    mEmojiEditText.append(content);
                }

                @Override
                public void onBackspaceClickListener() {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    mEmojiEditText.dispatchKeyEvent(event);
                }
            };
        }
        return mListener;
    }

    @Override
    public void onBackPressed() {
        if (mAdjust.isEmoticonSheetLayoutShow()) {
            mAdjust.setInputState(AdjustEmoticonSheet.INPUT_STATE_HIDE_ALL);
        } else {
            super.onBackPressed();
        }
    }

    class AdjustEmoticonSheet {
        public static final int INPUT_STATE_HIDE_ALL = 0;
        public static final int INPUT_STATE_ONLY_KEY_BOARD = 1;
        public static final int INPUT_STATE_ONLY_EMOJI_SHEET = 2;
        public static final int INPUT_STATE_HIDE_EMOJI_SHEET = 3;

        private void hideEmojiSheet() {
            Log.i("NewActivity", "hideEmojiSheet");
            mEmoticonSheetLayout.setVisibility(View.GONE);
            mEmoticonSheetLayoutDivideView.setVisibility(View.GONE);
        }

        private void showEmojiSheet() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEmoticonSheetLayout.setVisibility(View.VISIBLE);
                    mEmoticonSheetLayoutDivideView.setVisibility(View.VISIBLE);
                }
            }, 240);
        }

        private void showOnlyEmojiSheet() {
            Log.i("NewActivity", "showOnlyEmojiSheet");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            InputUtil.hideKeyboard(NewActivity.this);
            showEmojiSheet();
        }

        private void showOnlyKeyboard() {
            Log.i("NewActivity", "showOnlyKeyboard");
            hideEmojiSheet();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            InputUtil.showKeyboard(NewActivity.this);
        }

        private void hideAll() {
            Log.i("NewActivity", "hideAll");
            hideEmojiSheet();
            InputUtil.hideKeyboard(NewActivity.this);
        }

        public boolean isEmoticonSheetLayoutShow() {
            return mEmoticonSheetLayout.getVisibility() == View.VISIBLE;
        }

        public void setInputState(int inputState) {
            Log.i("NewActivity", "inputState = " + inputState);
            if (mEmoticonSheetLayout == null || mEmoticonSheetLayoutDivideView == null) {
                return;
            }

            switch (inputState) {
                case INPUT_STATE_HIDE_ALL:
                    hideAll();
                    break;
                case INPUT_STATE_ONLY_KEY_BOARD:
                    showOnlyKeyboard();
                    break;
                case INPUT_STATE_ONLY_EMOJI_SHEET:
                    showOnlyEmojiSheet();
                    break;
                case INPUT_STATE_HIDE_EMOJI_SHEET:
                    hideEmojiSheet();
                    break;
            }
        }

        public void exchange() {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            if (isEmoticonSheetLayoutShow()) {
                hideEmojiSheet();
                InputUtil.changeKeyboard(NewActivity.this);
                //showOnlyKeyboard();
            } else {
                //showEmojiSheet();
                showOnlyEmojiSheet();
            }

            //InputUtil.changeKeyboard(NewActivity.this);
        }
    }
}
