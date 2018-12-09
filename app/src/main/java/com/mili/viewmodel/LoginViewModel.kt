package com.mili.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lazy.library.logging.Logcat
import com.mili.base.BaseViewModel
import com.mili.net.APIService
import com.mili.net.api.LoginApi
import com.mili.net.bean.LoginCodeRequest
import com.mili.net.bean.RegisterCodeRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by TeeMo111 on 2018/12/7.
 */
class LoginViewModel : BaseViewModel() {
    // 生成一个api的常量
    private val apiLogin = APIService.createAPI(LoginApi::class.java)

    // 定义一个手机号的变量
    val mobile = MutableLiveData<String>()
    // 定义一个短信验证码的变量
    val smsCode = MutableLiveData<String>()

    // 定义一个监听的接口 v中持有
    var listener: ViewModelListener? = null

    private var isLogin = false

    interface ViewModelListener {
        // 请求验证码成功监听
        fun onCodeSuccess()

        //  请求登录成功监听
        fun onLoginSuccess()

        //  请求注册成功监听
        fun onRegisterSuccess()
    }

    /**
     * 1. 判断手机号是否存在(是否已经注册过)
     * 2. true：直接发送登录验证码
     * 3. false：直接发送注册验证码
     */
    fun requestSmsCode() {
        // 加载样式
        showLoading()
        // 模拟手机号
//        smsCode.value = "17788664516"
        // 短信验证码取得请求
        smsCode.value?.run {
            Observable.just(apiLogin)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        apiLogin.isMobileExist(LoginCodeRequest(this.toLong()))
                    }
                    .flatMap {
                        Logcat.d("isMobileExist --> ")
                        if (it.data.exist == 1) {
                            isLogin = true
                            apiLogin.loginCode(LoginCodeRequest(this.toLong()))
                        } else {
                            isLogin = false
                            apiLogin.registerCode(RegisterCodeRequest(this.toLong()))
                        }

                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        if (isLogin) {
                            Logcat.d("loginCode --> ")
                        } else {
                            Logcat.d("registerCode --> ")
                        }
                        if (it.data.toString().length == 1)
                            smsCode.value = it.data.toString()
                    }
                    .subscribeBy(
                            onComplete = { listener?.onCodeSuccess() },
                            onError = {
                                hideLoading()
                                error(it)
                            }
                    )
        }
    }
}