package com.nirvana.code.mvp.presenter.login

import com.nirvana.code.mvp.model.login.KLoginModel
import com.nirvana.code.mvp.view.login.KLoginView
import com.nirvana.code.utils.BackgroundThread

class KLoginPresenter {
    var mKLoginModel:KLoginModel = KLoginModel()
    lateinit var mKLoginView:KLoginView

    //    constructor(kLoginModel: KLoginModel){
//
//    }

    fun bind(kLoginView: KLoginView){
        mKLoginView = kLoginView
    }

    fun login(){
        mKLoginView?.showLoading()
        BackgroundThread.getInstance()?.postDelayed(Runnable {
            mKLoginModel?.login(object:KLoginModel.KLoginListener{
                override fun onLoginSuccess() {
                    mKLoginView?.loginSuccess()
                }

                override fun onLoginFail() {
                    mKLoginView?.loginFail()
                }

            })
        },500)

    }
}