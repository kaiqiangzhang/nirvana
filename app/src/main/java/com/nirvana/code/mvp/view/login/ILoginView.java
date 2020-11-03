package com.nirvana.code.mvp.view.login;

public interface ILoginView {
    void loginSuccess();
    void loginFail();
    void dismissLoading();
    void showLoading();
}
