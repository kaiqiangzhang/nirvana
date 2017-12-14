package com.nirvana.code.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;


import com.nirvana.code.widgets.swipeback.PrefUtil;
import com.nirvana.code.widgets.swipeback.SwipeBackLayout;


/**
 *
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected SwipeBackLayout mSwipeBackLayout;
    public T mPresenter;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        setTheme(PrefUtil.getThemeRes());
        createPresenter();
        if (mPresenter !=null){
            mPresenter.attachView(this);
        }
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter){
            mPresenter.detachView();
        }
    }

    public abstract int getContentViewId();

    public abstract int getFragmentContentViewId();


    /**
     * function:添加Fragment
     * note:通过调用 addToBackStack(String tag), replace事务被保存到back stack, 因此用户可以回退事务,并通过按下BACK按键带回前一个fragment，
     * 如果没有调用 addToBackStack(String tag),那么当事务提交后, 那个fragment会被销毁,并且用户不能导航回到它。
     * 其中参数tag将作为本次加入BackStack的Transaction的标志。commitAllowingStateLoss()，这种提交是允许发生异常时状态值丢失的情况下也能正常提交事物。
     *
     * @param fragment
     */
    public void addFragment(BaseFragment fragment) {
        if (null != fragment && getFragmentContentViewId() != 0) {
            String tag = fragment.getClass().getSimpleName();
            getSupportFragmentManager().beginTransaction().replace(getFragmentContentViewId(), fragment, tag)
                    .addToBackStack(tag);
        }
    }

    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {//获取回退栈中所有事务数量，大于1的时候，执行回退操作
            getSupportFragmentManager().popBackStack();
        } else {//等于1的时候，代表当前Activity只剩下一个Fragment，直接finish()当前Activity即可
            finish();
        }
    }

    public abstract void createPresenter();
    public abstract void initView();
    public abstract void initData();
}
