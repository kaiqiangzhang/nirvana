package com.nirvana.code.mvp.model.login;

public class LoginModel {
    /**
     * 带一个回调用于处理异步
     * @param loginListener
     */
    public void login(ILoginListener loginListener){
        loginListener.onLoginSuccess();
        loginListener.onLoginFail();
    }

    public interface ILoginListener{
        void onLoginSuccess();
        void onLoginFail();
    }
}
