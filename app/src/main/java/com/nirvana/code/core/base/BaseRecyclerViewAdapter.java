package com.nirvana.code.core.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nirvana.product.mainpage.bean.HotTagBean;

import java.util.List;

/**
 * Created by kriszhang on 2017/8/7.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<T> mDatas;
    public int mLayoutId;
    private LayoutInflater mInflater;
    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(mInflater.inflate(mLayoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    public T getItem(int position){
        return mDatas.get(position);
    }
    public abstract void bindData(BaseViewHolder holder, T data, int position);

    public interface OnItemClickListener{
        void onItemClickListener(View view, int position);
    }

    public interface OnItemLongClickListener{
        void OnItemLongClickListener();
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void refreshData(List<T> datas){
        mDatas = datas;
        notifyItemRangeChanged(0,mDatas.size());
    }
}
