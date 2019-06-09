package com.mili.mvp.ui

import android.util.Log
import com.hjq.toast.ToastUtils
import com.mili.R
import com.mili.mvp.base.BaseMVPActivity
import com.mili.mvp.contract.MVPContract
import com.mili.mvp.presenter.MVPPresenter
import kotlinx.android.synthetic.main.activity_mvp.*

/**
 * @author: Big Strong
 * @date  : 2019-06-05
 * @desc  : MVP中的View
 */
class MVPActivity : BaseMVPActivity(), MVPContract.View {
    companion object {
        private const val TAG = "MVPActivity"
    }

    private val mPresenter by lazy {
        MVPPresenter()
    }

    override fun getLayoutId(): Int = R.layout.activity_mvp

    override fun initView() {

        mPresenter.attachView(this)

        btn_load_first.setOnClickListener {
            mPresenter.requestHomeData()
        }
        btn_load_more.setOnClickListener {
            mPresenter.requestMoreData()
        }
    }

    override fun setHomeData(homeData: String) {
        Log.d(TAG, "setHomeData-->$homeData")
    }

    override fun setMoreData(moreData: String) {
        Log.d(TAG, "setMoreData-->$moreData")
    }

    override fun setErrorData(errorData: String) {
        Log.d(TAG, "setErrorData-->$errorData")
    }

    override fun showLoading() {
        ToastUtils.show("开始加载...")
    }

    override fun dismissLoading() {
        ToastUtils.show("结束加载...")
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}








