package com.mili.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hjq.toast.ToastUtils
import com.lazy.library.logging.Logcat
import com.mili.R
import com.mili.app.Constants.TAG
import com.mili.databinding.ActivityKotlinBinding
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
        editCode.setText(viewModel.smsCode.value)
    }

    override fun onLoginSuccess() {
        ToastUtils.show("登录成功")
    }

    override fun onRegisterSuccess() {
        ToastUtils.show("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val label = intent.getStringExtra("label")
        // setContentView(R.layout.activity_kotlin)
        val mBind = DataBindingUtil.setContentView<ActivityKotlinBinding>(this, R.layout.activity_kotlin)
        // 标题栏赋值
        common_toolbar_title_tv.text = label

        // 创建token(类似于sp) 用于保存请求生成的登录标识
        userToken = UserToken(this)

        // 创建一个viewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.listener = this

        mBind.viewModel = viewModel
        // 这句会导致一种无线循环的调用
        // mBind.setLifecycleOwner(this)

        // 定义一个手机号的观察者
        viewModel.mobile.observe(this, Observer {
            if (it.length == 6) {
                viewModel.mobile.value = "15560299554"
                Logcat.d(TAG, "requestMobileCode --> start")
                viewModel.requestSmsCode()
            }
        })

        // 定义一个验证码的观察者
        viewModel.smsCode.observe(this, Observer {
            Logcat.d(TAG, "requestSmsCode --> start")
            if (it.length == 4) {
                viewModel.requestLogin("17788664516")
            }
        })
    }
}