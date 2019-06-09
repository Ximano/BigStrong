package com.mili.mvp.base

/**
 * @author: Big Strong
 * @date  : 2019-06-09
 * @desc  : P层的基类，实现了P层与V层的绑定和解绑
 * (RxJava中订阅的相关也可以在此处处理)
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {
    var mRootView: T? = null
        private set

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
    }
}