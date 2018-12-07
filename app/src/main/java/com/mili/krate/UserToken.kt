package com.mili.krate

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.stringPref

/**
 * Created by TeeMo111 on 2018/12/7.
 */
class UserToken(context: Context) : SimpleKrate(context) {
    companion object {
        const val KEY_ACCESS_TOKEN = "AccessToken"
        const val KEY_REFRESH_TOKEN = "RefreshToken"
    }

    // 将一个key保存在对应的一个变量中
    var accessToken by stringPref(KEY_ACCESS_TOKEN)
    var refreshToken by stringPref(KEY_ACCESS_TOKEN)
}