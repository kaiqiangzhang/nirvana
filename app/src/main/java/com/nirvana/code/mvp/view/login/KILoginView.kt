package com.nirvana.code.mvp.view.login

interface KILoginView {
    fun loginSuccess()
    fun loginFail()
    fun dismissLoading()
    fun showLoading()
}