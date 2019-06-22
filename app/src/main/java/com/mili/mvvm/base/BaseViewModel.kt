package com.mili.mvvm.base

import android.arch.lifecycle.ViewModel
import com.hjq.toast.ToastUtils
import com.lazy.library.logging.Logcat

/**
 * @author: Big Strong
 * @date  : 2019/6/22
 * @desc  :
 */
typealias OnViewModelSuccess = () -> Unit
open class BaseViewModel : ViewModel() {

    companion object {
        private const val TAG = "ERROR"
    }

    fun error(throwable: Throwable) {
        // todo 处理异常操作
        Logcat.d(TAG, throwable.toString())
    }

    fun toastMsg(message: String) {
        // todo 提示操作
        ToastUtils.show(message)
    }

    fun showLoading() {
        // todo 显示加载
    }

    fun hideLoading() {
        // todo 隐藏加载
    }
}