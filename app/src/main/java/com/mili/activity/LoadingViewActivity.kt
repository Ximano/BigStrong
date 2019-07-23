package com.mili.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lazy.library.logging.Logcat

import com.mili.R
import com.mili.fragment.LoadingDialogFragment
import com.mili.widget.LoadingView
import kotlinx.android.synthetic.main.loading_view_activity.*

/**
 * @author: Strong
 * @date : 2019-07-22
 * @dec : LoadingView的样式
 */
class LoadingViewActivity : AppCompatActivity() {
    private var progressBarLoading: LoadingView? = null
    private var fragmentLoading: LoadingDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_view_activity)
        findViewById<View>(R.id.tv_show1).setOnClickListener {
            showProgressBar("上传中...")
        }

        findViewById<View>(R.id.tv_hide1).setOnClickListener {
            hideProgressBar()
        }

        tv_show2.setOnClickListener {
            showLoading()
        }

        tv_hide2.setOnClickListener {
            hideLoading()
        }
    }


    private fun showProgressBar(string: String? = "加载中") {
        if (progressBarLoading == null) {
            progressBarLoading = LoadingView(this)
        }
        progressBarLoading?.show()
    }

    private fun hideProgressBar() {
        progressBarLoading?.hide()
    }

    /** 显示加载**/
    fun showLoading() {
        if (fragmentLoading == null) {
            Logcat.d(this.localClassName.toString(), "${this.javaClass.simpleName} loading new 成功了吗?")
            fragmentLoading = LoadingDialogFragment()
        }

        fragmentLoading?.show(supportFragmentManager, "loading")
        Logcat.d(this.localClassName.toString(), "${this.javaClass.simpleName} 收到消息开启loading")
    }

    /** 隐藏加载**/
    fun hideLoading() {
        fragmentLoading?.dismiss()
        Logcat.d(this.localClassName.toString(), "${this.javaClass.simpleName} 收到消息关闭loading")
    }
}
