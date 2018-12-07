package com.mili.net.apibody

import com.mili.net.utils.ERROR_CODE_SUCCESS

data class APIResponseBody(val code: String? = ERROR_CODE_SUCCESS, val msg: String?, val timestamp: Long, val nonce: String?, val signature: String?, val data: String?)