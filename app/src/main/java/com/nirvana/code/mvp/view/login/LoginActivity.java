package com.nirvana.code.mvp.view.login;

import com.nirvana.code.core.base.BaseActivity;
import com.nirvana.code.mvp.presenter.login.LoginPresenter;

public class LoginActivity extends BaseActivity implements ILoginView{
    private LoginPresenter mLoginPresenter;
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getFragmentContentViewId() {
        return 0;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.bind(this);
        mLoginPresenter.login();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showLoading() {

    }
}
