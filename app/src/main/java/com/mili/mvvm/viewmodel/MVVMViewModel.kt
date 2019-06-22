package com.mili.mvvm.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.mili.mvvm.base.BaseViewModel
import com.mili.mvvm.base.OnViewModelSuccess
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

/**
 * @author: Big Strong
 * @date  : 2019/6/22
 * @desc  :
 */
class MVVMViewModel : BaseViewModel() {
    companion object {
        private const val TAG = "MVVMViewModel"
    }

    val text = MutableLiveData<String>()

    init {
        text.value = "原始数据"
    }

    @SuppressLint("CheckResult")
    fun refreshUI(isLogin: Boolean? = true, onSuccess: OnViewModelSuccess = {}) {
        Observable.just("数据源")
                .map {
                    Log.d(TAG, "map-->$it")
                    if (isLogin!!)
                        "$it--登录"
                    else
                        "$it--注册"
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d(TAG, "doOnNext-->$it")
                    text.postValue(it)
                }
                .subscribeBy(
                        onComplete = {
                            onSuccess()
                        },
                        onError = {
                            error(it)
                        }
                )
    }


    fun toRegister() {
        refreshUI(false) {
            Log.d(TAG, "toRegister-->注册操作完成")
        }
    }

    fun toLogin() {
//        refreshUI {
//            Log.d(TAG, "toLogin-->登录操作完成")
//        }
        text.postValue("登录操作完成")
    }
}