package com.zeal.android.emoticonsheet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zeal.android.emoticonsheet.emoticon.Emoticon;
import com.zeal.android.emoticonsheet.fragment.EmoticonGroupFragment;
import com.zeal.android.emoticonsheet.layout.PagerSlidingTabStrip;
import com.zeal.android.emoticonsheet.provider.IEmoticonDoubleWrapper;

import java.util.ArrayList;

public class EmoticonGroupViewPagerAdapter extends FragmentPagerAdapter
        implements PagerSlidingTabStrip.IconTabProvider {
    private static final String TAG = "EmoticonGroupViewPagerAdapter";

    private IEmoticonDoubleWrapper mDoubleWrapper;

    public EmoticonGroupViewPagerAdapter(FragmentManager fm, IEmoticonDoubleWrapper doubleWrapper) {
        super(fm);
        mDoubleWrapper = doubleWrapper;
    }

    @Override
    public Fragment getItem(int position) {
        return EmoticonGroupFragment.newInstance(0,
                (ArrayList<Emoticon>) mDoubleWrapper.getEmoticons(position));
    }

    @Override
    public int getPageIconResId(int position) {
        return mDoubleWrapper.getIcon(position);
    }

    @Override
    public int getCount() {
        return mDoubleWrapper.getCount();
    }

    public void setEmoticonDoubleWrapper(IEmoticonDoubleWrapper doubleWrapper) {
        mDoubleWrapper = doubleWrapper;
        notifyDataSetChanged();
    }
}
