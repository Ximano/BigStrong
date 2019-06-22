package com.mili.mvp.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * @author: Big Strong
 * @date  : 2019-06-08
 * @desc  : Activity的基类, Activity的公共方法等
 */
abstract class BaseMVPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    /**设置布局 */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**初始化View*/
    abstract fun initView()
}
