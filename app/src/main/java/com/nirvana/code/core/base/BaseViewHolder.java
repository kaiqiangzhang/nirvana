package com.nirvana.code.core.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by kriszhang on 2017/8/7.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView(){
        return itemView;
    }
}
