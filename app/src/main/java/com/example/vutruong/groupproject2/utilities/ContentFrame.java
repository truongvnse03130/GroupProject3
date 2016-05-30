package com.example.vutruong.groupproject2.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.vutruong.groupproject2.R;

/**
 * Created by Administrator on 30/05/2016.
 */
public class ContentFrame extends FrameLayout {
    private ViewGroup mDataLayout;
    private LayoutInflater mInflater;

    public ContentFrame(Context context) {
        this(context, null, 0);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.loading_layout, this, false);
        view.setVisibility(View.GONE);
        addView(view);
        view = mInflater.inflate(R.layout.error_layout, this, false);
        view.setVisibility(View.GONE);
        addView(view);
    }

    public ContentFrame(Context context, AttributeSet attributeset) {
        this(context, attributeset, 0);
    }

    public ContentFrame(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.loading_layout, this, false);
        view.setVisibility(View.GONE);
        addView(view);
        view = mInflater.inflate(R.layout.error_layout, this, false);
        view.setVisibility(View.GONE);
        addView(view);
    }

    public ViewGroup getDataLayout() {
        return mDataLayout;
    }

    public void setDataLayout(int layoutRes, int resId) {
        setDataLayout(mInflater, layoutRes, resId);
    }

    public void setDataLayout(LayoutInflater layoutinflater, int layoutRes,
                              int resId) {
        if (layoutRes != 0) {
            mDataLayout = (ViewGroup) layoutinflater.inflate(layoutRes, this,
                    false);
            if (resId != 0)
                mDataLayout.setId(resId);
            addView(mDataLayout);
        }
    }
}
