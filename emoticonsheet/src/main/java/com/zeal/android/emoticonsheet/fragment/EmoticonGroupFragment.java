package com.zeal.android.emoticonsheet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zeal.android.emoticonsheet.R;
import com.zeal.android.emoticonsheet.adapter.EmoticonImageAdapter;
import com.zeal.android.emoticonsheet.adapter.EmoticonPagingViewPagerAdapter;
import com.zeal.android.emoticonsheet.emoticon.Emoticon;
import com.zeal.android.emoticonsheet.event.IEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.event.OnEmoticonSheetLayoutListener;

import java.util.ArrayList;
import java.util.List;

public class EmoticonGroupFragment extends Fragment {
    private static final String TAG = "EmoticonGroupFragment";

    private static final String ARG_PAGE_AT = "page_at";
    private static final String ARG_EMOTICONS = "emoticons";

    private int mPageAt;
    private ArrayList<Emoticon> mEmoticons;
    private OnEmoticonSheetLayoutListener mListener;

    private int mEmojiColumn = 7;
    private int mEmojiRow = 4;

    private ViewPager mEmoticonPagingViewPager;
    private EmoticonPagingViewPagerAdapter mAdapter;
    private List<View> mEmoticonPagingViews;

    private LinearLayout mPageIndicator;

    public static EmoticonGroupFragment newInstance(int pageAt, ArrayList<Emoticon> emoticons) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_AT, pageAt);
        args.putParcelableArrayList(ARG_EMOTICONS, emoticons);
        EmoticonGroupFragment fragment = new EmoticonGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageAt = getArguments().getInt(ARG_PAGE_AT, 0);
        mEmoticons = getArguments().getParcelableArrayList(ARG_EMOTICONS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emoticon_group, container, false);
        mEmoticonPagingViewPager = (ViewPager) v.findViewById(R.id.emoticon_paging_view_pager);
        mPageIndicator = (LinearLayout) v.findViewById(R.id.emoji_page_indicator);

        mEmoticonPagingViews = new ArrayList<>();
        int emojiPerPage = mEmojiColumn * mEmojiRow - 1;
        int size = mEmoticons.size();
        int round = (size % emojiPerPage == 0) ? size / emojiPerPage : (size / emojiPerPage + 1);
        for (int i = 0; i < round; i++) {
            // init gridView
            GridView gridView = (GridView) LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_emoticon_paging, container, false);
            gridView.setNumColumns(mEmojiColumn);

            int start = i * emojiPerPage;
            int end = (i + 1) * emojiPerPage;
            end = (end > mEmoticons.size()) ? (mEmoticons.size()) : end;

            EmoticonImageAdapter emoticonImageAdapter = new EmoticonImageAdapter(getContext(),
                    mEmoticons.subList(start, end));
            //TODO
            checkOnEmoticonSheetLayoutListener();
            emoticonImageAdapter.setEmoticonSheetLayoutListener(mListener);

            gridView.setAdapter(emoticonImageAdapter);
            mEmoticonPagingViews.add(gridView);

            // init page indicator
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(R.drawable.point_normal);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.point_selected);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 8;
            layoutParams.height = 8;
            mPageIndicator.addView(imageView, layoutParams);
        }

        mAdapter = new EmoticonPagingViewPagerAdapter(mEmoticonPagingViews);
        mEmoticonPagingViewPager.setAdapter(mAdapter);
        mEmoticonPagingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int j = 0; j < mPageIndicator.getChildCount(); j++) {
                    if (j == position) {
                        mPageIndicator.getChildAt(j).setBackgroundResource(R.drawable.point_selected);
                    } else {
                        mPageIndicator.getChildAt(j).setBackgroundResource(R.drawable.point_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mEmoticonPagingViewPager.setCurrentItem(mPageAt);

        return v;
    }

    public void checkOnEmoticonSheetLayoutListener() {
        IEmoticonSheetLayoutListener iListener;
        if (mListener != null) {
            return;
        }

        if (getParentFragment() instanceof IEmoticonSheetLayoutListener) {
            iListener = (IEmoticonSheetLayoutListener) getParentFragment();
            mListener = iListener.getOnEmoticonSheetLayoutListener();
        } else if (getActivity() instanceof IEmoticonSheetLayoutListener) {
            iListener = (IEmoticonSheetLayoutListener) getActivity();
            mListener = iListener.getOnEmoticonSheetLayoutListener();
        }
    }
}
