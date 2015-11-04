package com.zeal.android.emoticonsheet.layout;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zeal.android.emoticonsheet.R;
import com.zeal.android.emoticonsheet.adapter.EmoticonGroupViewPagerAdapter;
import com.zeal.android.emoticonsheet.provider.IEmoticonDoubleWrapper;

public class EmoticonSheetLayout extends RelativeLayout {
    private ViewPager mEmoticonGroupViewPager;
    private EmoticonGroupViewPagerAdapter mAdapter;
    private PagerSlidingTabStrip mEmoticonGroupTab;

    public EmoticonSheetLayout(Context context) {
        this(context, null);
    }

    public EmoticonSheetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmoticonSheetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_emoticon_sheet, this, true);
        mEmoticonGroupViewPager = (ViewPager) findViewById(R.id.emoticon_group_view_pager);
        mEmoticonGroupTab = (PagerSlidingTabStrip) findViewById(R.id.emoticon_group_tab);
    }

    public void setAdapter(FragmentManager fm, IEmoticonDoubleWrapper doubleWrapper) {
        if (mAdapter == null) {
            mAdapter = new EmoticonGroupViewPagerAdapter(fm, doubleWrapper);
            mEmoticonGroupViewPager.setAdapter(mAdapter);
            mEmoticonGroupTab.setViewPager(mEmoticonGroupViewPager);
            if (doubleWrapper.getCount() < 2) {
                mEmoticonGroupViewPager.setVisibility(View.GONE);
            }
        }
    }
}
