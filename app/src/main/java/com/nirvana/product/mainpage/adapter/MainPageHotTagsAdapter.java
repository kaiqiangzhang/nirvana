package com.nirvana.product.mainpage.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nirvana.code.R;
import com.nirvana.code.core.base.BaseRecyclerViewAdapter;
import com.nirvana.code.core.base.BaseViewHolder;
import com.nirvana.code.utils.SelectToolUtil;
import com.nirvana.product.mainpage.bean.HotTagBean;

import java.util.List;

/**
 * Created by kriszhang on 2017/8/7.
 */

public class MainPageHotTagsAdapter extends BaseRecyclerViewAdapter<HotTagBean> {
    public MainPageHotTagsAdapter(Context context, List<HotTagBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void bindData(BaseViewHolder holder, HotTagBean data, final int position) {
        if (data == null){
            Log.d("MainPageHotTagsAdapter","data == null in method bindData()");
            return;
        }
        TextView tagNameView=holder.getView(R.id.hot_tag_name);
        if (tagNameView !=null){
            tagNameView.setText(data.getName()+"("+data.getPosts()+")");
        }
        SelectToolUtil.setAlphaSelector(holder.getRootView());
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(v,position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (position%2== 0){
             mLayoutId = R.layout.hot_tag_item;
        }else {
            mLayoutId= R.layout.hot_tag_item_green;
        }
        return super.getItemViewType(position);
    }

    /** 对TextView设置不同状态时其文字颜色。 */
    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /** 设置Selector。 */
    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,
                                                int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }



}
