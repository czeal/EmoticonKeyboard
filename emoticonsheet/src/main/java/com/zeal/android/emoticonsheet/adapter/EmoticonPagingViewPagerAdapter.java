package com.zeal.android.emoticonsheet.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class EmoticonPagingViewPagerAdapter extends PagerAdapter {
    private List<View> mEmoticonPagingViews;

    public EmoticonPagingViewPagerAdapter(List<View> emoticonPagingViews) {
        mEmoticonPagingViews = emoticonPagingViews;
    }

    @Override
    public int getCount() {
        return mEmoticonPagingViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mEmoticonPagingViews.get(position));
        return mEmoticonPagingViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mEmoticonPagingViews.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
