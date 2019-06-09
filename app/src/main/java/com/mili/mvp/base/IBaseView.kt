package com.mili.mvp.base

/**
 * @author: Big Strong
 * @date  : 2019-06-09
 * @desc  : V层的接口，定义规则。
 * 每个View都需要遵循的规则或者具备的属性
 */
interface IBaseView {
    // 显示加载效果
    fun showLoading()
    // 隐藏加载效果
    fun dismissLoading()
}