package com.nirvana.code.mvp.presenter.login;

import com.nirvana.code.mvp.model.login.LoginModel;
import com.nirvana.code.mvp.view.login.ILoginView;

public class LoginPresenter {
    private LoginModel mLoginModel;
    private ILoginView mLoginView;

    /**
     * 在构造方法中实例化Model对象
     */
    public LoginPresenter() {
        this.mLoginModel = new LoginModel();
    }

    public void bind(ILoginView loginView){
        this.mLoginView = loginView;
    }

    public void login(){
        mLoginView.showLoading();
        mLoginModel.login(new LoginModel.ILoginListener() {
            @Override
            public void onLoginSuccess() {

                mLoginView.loginSuccess();
                mLoginView.dismissLoading();
            }

            @Override
            public void onLoginFail() {

                mLoginView.loginFail();
                mLoginView.dismissLoading();
            }
        });
    }

}
