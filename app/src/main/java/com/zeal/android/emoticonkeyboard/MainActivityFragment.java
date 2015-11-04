package com.zeal.android.emoticonkeyboard;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.zeal.android.emoticonkeyboard.util.InputUtil;
import com.zeal.android.emoticonsheet.event.IEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.event.OnEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.font.EmojiFontHelper;
import com.zeal.android.emoticonsheet.layout.EmoticonSheetLayout;
import com.zeal.android.emoticonsheet.provider.EmoticonProvider;
import com.zeal.android.emoticonsheet.provider.EmoticonSparseMap;
import com.zeal.android.emoticonsheet.view.EmoticonEditText;
import com.zeal.android.emoticonsheet.view.EmoticonTextView;

/**
 * this will be remove later, see the NewActivity
 */
public class MainActivityFragment extends Fragment implements IEmoticonSheetLayoutListener {
    private static final String TAG = "MainActivityFragment";

    private static final String ARG_INPUT_STATE = "input_state";
    public static final int INPUT_STATE_HIDE_ALL = 0;
    public static final int INPUT_STATE_ONLY_KEY_BOARD = 1;
    public static final int INPUT_STATE_ONLY_EMOJI_SHEET = 2;
    public static final int INPUT_STATE_HIDE_EMOJI_SHEET = 3;
    private int mInputState;

    private EmoticonEditText mEmojiEditText;
    private EmoticonTextView mEmojiTextView;

    private ImageView mTakePhoto;
    private ImageView mPickImages;
    private ImageView mCallSomebody;
    private ImageView mPickSharpTopic;
    private ImageView mPickEmojis;

    private View mEmoticonSheetLayoutDivideView;
    private EmoticonSheetLayout mEmoticonSheetLayout;
    private OnEmoticonSheetLayoutListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mInputState = savedInstanceState.getInt(ARG_INPUT_STATE, INPUT_STATE_ONLY_KEY_BOARD);
        } else {
            mInputState = INPUT_STATE_ONLY_KEY_BOARD;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mEmojiEditText = (EmoticonEditText) v.findViewById(R.id.emoji_edit_text);
        mEmojiTextView = (EmoticonTextView) v.findViewById(R.id.emoji_text_view);

        mTakePhoto = (ImageView) v.findViewById(R.id.action_take_photo);
        mPickImages = (ImageView) v.findViewById(R.id.action_pick_images);
        mCallSomebody = (ImageView) v.findViewById(R.id.action_call_somebody);
        mPickSharpTopic = (ImageView) v.findViewById(R.id.action_pick_sharp_topic);
        mPickEmojis = (ImageView) v.findViewById(R.id.action_pick_emojis);

        mEmoticonSheetLayoutDivideView = v.findViewById(R.id.emoticon_sheet_layout_divide_view);
        mEmoticonSheetLayout = (EmoticonSheetLayout) v.findViewById(R.id.emoticon_sheet_layout);
        setAdapter();

        mEmojiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputState(INPUT_STATE_HIDE_EMOJI_SHEET);
            }
        });
        mEmojiEditText.setEmoticonMap(EmoticonSparseMap.get(getContext()));
        //mEmojiEditText.setTypeface(EmojiFontHelper.getTypeface(getContext(), EmojiFontHelper.APPLE_SMALL));
        mEmojiTextView.setEmoticonMap(EmoticonSparseMap.get(getContext()));

        mEmojiEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmojiTextView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPickEmojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmoticonSheetLayoutShow()) {
                    setInputState(INPUT_STATE_ONLY_KEY_BOARD);
                } else {
                    setInputState(INPUT_STATE_ONLY_EMOJI_SHEET);
                }
            }
        });

        return v;
    }

    public void setAdapter() {
        EmoticonProvider provider = EmoticonProvider.get(getContext());
        mEmoticonSheetLayout.setAdapter(getChildFragmentManager(), provider.getDoubleWrapper());
    }

    @Override
    public void onResume() {
        super.onResume();
        // 如果为竖屏则设置只显示键盘
        if (mInputState == INPUT_STATE_ONLY_EMOJI_SHEET &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mInputState = INPUT_STATE_ONLY_KEY_BOARD;
        }
        setInputState(mInputState);
        Log.i(TAG, "onResume " + "mListener == null ? " + (mListener == null));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_INPUT_STATE, mInputState);
    }

    public boolean isEmoticonSheetLayoutShow() {
        return mEmoticonSheetLayout.getVisibility() == View.VISIBLE;
    }

    public void showOnlyEmojiSheet() {
        InputUtil.hideKeyboard(getActivity());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmoticonSheetLayout.setVisibility(View.VISIBLE);
                mEmoticonSheetLayoutDivideView.setVisibility(View.VISIBLE);
            }
        }, 240);
    }

    public void showOnlyKeyboard() {
        mEmoticonSheetLayout.setVisibility(View.GONE);
        mEmoticonSheetLayoutDivideView.setVisibility(View.GONE);
        InputUtil.showKeyboard(getActivity());
    }

    public void hideEmojiSheet() {
        mEmoticonSheetLayout.setVisibility(View.GONE);
        mEmoticonSheetLayoutDivideView.setVisibility(View.GONE);
    }

    public void hideAll() {
        mEmoticonSheetLayout.setVisibility(View.GONE);
        mEmoticonSheetLayoutDivideView.setVisibility(View.GONE);
        InputUtil.hideKeyboard(getActivity());
    }

    public void setInputState(int inputState) {
        mInputState = inputState;
        switch (mInputState) {
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
}
