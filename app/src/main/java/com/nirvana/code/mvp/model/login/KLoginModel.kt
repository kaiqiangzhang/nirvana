package com.nirvana.code.mvp.model.login

class KLoginModel {
    fun login(kLoginListener: KLoginListener){
        kLoginListener.onLoginSuccess()
        kLoginListener.onLoginFail()
    }

    interface KLoginListener{
        fun onLoginSuccess()
        fun onLoginFail()
    }
}