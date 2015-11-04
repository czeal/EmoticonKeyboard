package com.zeal.android.emoticonsheet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zeal.android.emoticonsheet.R;
import com.zeal.android.emoticonsheet.emoticon.Emoticon;
import com.zeal.android.emoticonsheet.event.OnEmoticonSheetLayoutListener;
import com.zeal.android.emoticonsheet.event.RepeatListener;

import java.util.List;

public class EmoticonImageAdapter extends BaseAdapter {
    private static final String TAG = "EmoticonImageAdapter";

    private Context mContext;
    private List<Emoticon> mEmoticons;
    private OnEmoticonSheetLayoutListener mListener;
    private Emoticon mDelete;

    public EmoticonImageAdapter(Context context, List<Emoticon> emoticons) {
        mContext = context;
        mEmoticons = emoticons;
        mDelete = new Emoticon(0, 0);
    }

    @Override
    public int getCount() {
        return mEmoticons.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < mEmoticons.size()) {
            return mEmoticons.get(position);
        } else {
            return mDelete;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // TODO
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_emoticon_image, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.emoticon_image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int icon;
        final String content;
        if (position < mEmoticons.size()) {
            icon = mEmoticons.get(position).getIcon();
            content = mEmoticons.get(position).getContent();

            viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(icon));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onEmoticonClickListener(content);
                    }
                }
            });
        } else {
            icon = R.drawable.delete;
            viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(icon));
            convertView.setOnTouchListener(new RepeatListener(1000, 50, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onBackspaceClickListener();
                    }
                }
            }));
        }

        return convertView;
    }

    public void setEmoticonSheetLayoutListener(OnEmoticonSheetLayoutListener listener) {
        mListener = listener;
    }

    private class ViewHolder {
        public ImageView imageView;
    }
}
