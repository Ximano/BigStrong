package com.mili.net

import com.jaqen.buhuaxin.net.converter.EncryptConverterFactory
import com.mili.net.validate.ProxyHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Proxy


object APIService {

    private val retrofit: Retrofit

    init {
        val httpLogging = HttpLoggingInterceptor()

        httpLogging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
                //.addInterceptor(httpLogging)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(EncryptConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
    }

    fun <T> createAPI(api: Class<T>): T {

        return retrofit.create(api)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> createTokenValidateAPI(api: Class<T>): T {
        val apiObj = retrofit.create(api)

//        return apiObj
        return Proxy.newProxyInstance(api.classLoader, arrayOf<Class<*>>(api), ProxyHandler(apiObj as Any)) as T
    }
}