package com.mili.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lazy.library.logging.Logcat
import com.mili.R
import com.mili.krate.UserToken
import com.mili.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_kotlin.*
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * Created by TeeMo111 on 2018/12/6.
 */
class KotlinActivity : AppCompatActivity(), LoginViewModel.ViewModelListener {

    // 定义一个类型, 初始化为null
    private var userToken: UserToken? = null
    // 定义一个类型, 咱无初始化
    private lateinit var viewModel: LoginViewModel

    override fun onCodeSuccess() {
        // 验证码发送成功, 显示验证码输入框
        editCode.visibility = View.VISIBLE
    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRegisterSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val label = intent.getStringExtra("label")
        setContentView(R.layout.activity_kotlin)
        // 标题栏赋值
        common_toolbar_title_tv.text = label

        // 创建token(类似于sp) 用于保存请求生成的登录标识
        userToken = UserToken(this)

        // 创建一个viewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.listener = this

        // 定义一个手机号的观察者
//        viewModel.mobile.observe(this, Observer {
//            if (it?.length == 6) {
//                Logcat.d("requestSmsCode --> start")
//                viewModel.requestSmsCode()
//            }
//        })

        // 定义一个验证码的观察者
        viewModel.smsCode.observe(this, Observer {
            if (it?.length == 4) {
                TODO("即将执行过去验证码的操作")
            }
        })

    }
}