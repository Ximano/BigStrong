package com.mili.net.api

import com.mili.net.apibody.APIData
import com.mili.net.bean.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by TeeMo111 on 2018/12/7.
 */
interface LoginApi {
    // 检测手机号是否存在 true：直接登录 false：直接注册
    @POST("user/mobilecheck")
    fun isMobileExist(@Body body: LoginCodeRequest): Observable<APIData<MobileStatus>>

    // 发送登录验证码
    @POST("smscode/logincode")
    fun loginCode(@Body body: LoginCodeRequest): Observable<APIData<Int>>

    // 发送注册的验证码
    @POST("smscode/regcode")
    fun registerCode(@Body body: RegisterCodeRequest): Observable<APIData<Int>>

    // 登录
    @POST("/login")
    fun login(@Body body: LoginRequest): Observable<APIData<LoginResponse>>

    // 注册
    @POST("/register")
    fun register(@Body body: LoginRequest): Observable<APIData<LoginResponse>>
}