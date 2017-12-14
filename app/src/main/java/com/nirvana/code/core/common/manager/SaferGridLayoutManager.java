package com.nirvana.code.core.common.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by kriszhang on 2017/7/19.
 */

public class SaferGridLayoutManager extends GridLayoutManager {
    public SaferGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SaferGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public SaferGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
