package com.nirvana.code.mvp.view.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nirvana.code.R
import com.nirvana.code.mvp.presenter.login.KLoginPresenter
import com.nirvana.code.mvp.presenter.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class KLoginActivity : AppCompatActivity() ,KLoginView{
    lateinit var loginPresenter:KLoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initData()
        login_btn.setOnClickListener {
            loginPresenter.login()
        }
    }

    fun initData(){
        loginPresenter = KLoginPresenter()
        loginPresenter.bind(this)
    }

    override fun showLoading() {
        Toast.makeText(applicationContext,"正在登录……",Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess() {
        Toast.makeText(applicationContext,"登陆成功！",Toast.LENGTH_SHORT).show()
    }

    override fun loginFail() {
        Toast.makeText(applicationContext,"登陆失败！",Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoading() {

    }

}