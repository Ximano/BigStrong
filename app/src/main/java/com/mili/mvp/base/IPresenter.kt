package com.mili.mvp.base

/**
 * @author: Big Strong
 * @date  : 2019-06-09
 * @desc  : P层的接口，定义规则
 * 每个Presenter都需要遵循的规则或者具备的属性
 */
interface IPresenter<in V : IBaseView> {
    // 关联View
    fun attachView(mRootView: V)

    // 取消关联View
    fun detachView()
}