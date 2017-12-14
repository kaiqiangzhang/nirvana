package com.nirvana.code.core.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kirs.Zhang on 17/08/07.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    public BaseActivity mBaseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView,savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
          1.instanceof是一个运算符，此处判断是确保能安全转换
          2.安卓有一种特殊情况，就是在APP运行在后台的时候，系统资源紧张的时候会把APP的资源全部回收(杀死APP的进程)，这时候把APP再从后台返回到前台的时候，APP会重启。
          这种内存不足的情况会导致许多问题，其中之一就是Fragment调用getActivity()的地方却返回null，报了空指针异常。解决办法就是在Fragment基类里设置一个Activity
           mActivity的全局变量，在onAttach(Activity activity)（使用onAttach(Context context)）里赋值，使用mActivity代替getActivity()。
         */
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }



    /**
     * 获取Fragment所附加的Activity
     * @return
     */
    public BaseActivity getAttachedActivity(){
        return mBaseActivity;
    }

    public void addFragment(BaseFragment fragment){
        if (null != fragment){
            getAttachedActivity().addFragment(fragment);
        }

    }

    public void removeFragment(){
        getAttachedActivity().removeFragment();
    }
    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    public abstract void initView(View view,Bundle savedInstanceState);


}
