package com.jaqen.buhuaxin.net.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class EncryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBody = chain.request().body()

        /*Logger.t("Interceptor")
                .d(requestBody?.)*/

        return chain.proceed(chain.request())
    }
}