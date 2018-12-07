package com.mili.net.validate

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ProxyHandler(private val obj: Any): InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, vararg args: Any): Any {
        return (method?.invoke(obj, *args) as Observable<*>).subscribeOn(Schedulers.io())
    }
}