package com.mili.base

import android.arch.lifecycle.ViewModel
import com.hjq.toast.ToastUtils
import com.lazy.library.logging.Logcat
import com.mili.app.Constants.TAG

open class BaseViewModel : ViewModel() {
    fun error(throwable: Throwable) {
        throwable.printStackTrace()
        val beginIndex = this.toString().indexOfLast { it == '.' }
        val endIndex = this.toString().indexOfLast { it == '@' }
//        val endIndex = this.toString().lastIndexOf('1', 0)
        if (beginIndex != -1 && endIndex != -1) {
            Logcat.e(TAG, " --> " + this.toString().substring(beginIndex + 1, endIndex) + "-->" + throwable.toString())
        }
    }

    fun showLoading() {
        ToastUtils.show("start loading")
    }

    fun hideLoading() {
        ToastUtils.show("stop loading")
    }
}
