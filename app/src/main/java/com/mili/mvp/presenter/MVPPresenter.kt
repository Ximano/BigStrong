package com.mili.mvp.presenter

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.mili.app.App
import com.mili.mvp.base.BasePresenter
import com.mili.mvp.contract.MVPContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: Big Strong
 * @date  : 2019-06-09
 * @desc  : 实现 获取数据-->提供给View的具体操作
 */
class MVPPresenter : BasePresenter<MVPContract.View>(), MVPContract.Presenter {
    companion object {
        private const val TAG = "MVPPresenter"
    }

    @SuppressLint("CheckResult")
    override fun requestHomeData() {
        mRootView?.showLoading()
        Observable.just("我是第一页数据源头")
                .map {
                    Log.d(TAG, "map-->$it")
                    it + "map"
                }
//                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext {
                    Thread.sleep(3000)
                    Log.d(TAG, "doOnNext-->$it")
//                    Toast.makeText(App.getInstance(), it, Toast.LENGTH_LONG).show()
                }
                .subscribe(
                        {
                            mRootView?.run {
                                Log.d(TAG, "subscribe-->onNext")
                                dismissLoading()
                                setHomeData(it)
                            }
                        },
                        {
                            mRootView?.run {
                                Log.d(TAG, "subscribe-->onError")
                                dismissLoading()
                                setErrorData(it.message!!)
                            }
                        }
                )
    }

    @SuppressLint("CheckResult")
    override fun requestMoreData() {
        Observable.just("我是更多数据源头")
                .map {
                    Log.d(TAG, "map-->$it")
                    it + "map"
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d(TAG, "doOnNext-->$it")
                    Toast.makeText(App.getInstance(), it, Toast.LENGTH_LONG).show()
                }
                .subscribe(
                        {
                            mRootView?.run {
                                Log.d(TAG, "subscribe-->onNext")
                                dismissLoading()
                                setMoreData(it)
                            }
                        },
                        {
                            mRootView?.run {
                                Log.d(TAG, "subscribe-->onError")
                                dismissLoading()
                                setErrorData(it.message!!)
                            }
                        }
                )
    }
}