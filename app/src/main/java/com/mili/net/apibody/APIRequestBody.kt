package com.jaqen.buhuaxin.net.apibody


data class APIRequestBody(
        val data: String,
        val accessToken: String,
        val timeStamp: Long,
        val nonce: String,
        val signature: String,
        val suffix: String = "android")