package com.mili.net.apibody

data class APIData<T>(val code: String, val msg: String, val data: T)