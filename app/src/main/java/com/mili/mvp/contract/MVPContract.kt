package com.mili.mvp.contract

import com.mili.mvp.base.IBaseView

/**
 * @author: Big Strong
 * @date  : 2019-06-09
 * @desc  : 某一个页面的一个契约类，定义了该页面的View接口、Presenter接口
 */
class MVPContract {

    interface View : IBaseView {
        /**设置第一页数据*/
        fun setHomeData(homeData: String)

        /**设置更多数据*/
        fun setMoreData(moreData: String)

        /**设置错误信息*/
        fun setErrorData(errorData: String)
    }

    interface Presenter {
        /**请求第一页数据*/
        fun requestHomeData()

        /**加载更多数据*/
        fun requestMoreData()
    }
}