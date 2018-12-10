package com.mili.net.converter

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.mili.net.apibody.APIResponseBody
import com.mili.net.exceptions.ErrorException
import com.mili.net.utils.EncryptionUtil
import com.lazy.library.logging.Logcat
import com.mili.app.Constants.TAG
import com.mili.net.utils.ERROR_CODE_SIGNATURE_INVAILIED
import com.mili.utils.Utils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import java.lang.reflect.Type

class EncryptResponseBodyConverter<T>(private val type: Type, private val gson: Gson, private val typeAdapter: TypeAdapter<T>, private val encryptionUtil: EncryptionUtil) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {

        val response = value.string()
        val json = JSONObject(response)
        val code = json.optString("code", null)

        if (TextUtils.isEmpty(code)) {
            val apiResponseBody = gson.fromJson<APIResponseBody>(response, APIResponseBody::class.java)
            if (encryptionUtil.verSignature(apiResponseBody.signature!!, apiResponseBody.data!!, apiResponseBody.nonce!!, apiResponseBody.timestamp.toString())) {
                val data = encryptionUtil.decrypt(apiResponseBody.data)
                val result = typeAdapter.fromJson(data.data)
                //val result = gson.fromJson<APIDData>(data.data, APIDData::class.java)
                //val result = typeAdapter.fromJson(data.data)
                //gson.fromJson<APIData<Int>>(data.data, APIData<Integer>::class.java)
                Logcat.d()
//                        .tag(type.toString()).ln()
                        .tag(TAG).ln()
                        .msg(Utils.logDecode2CN(data.data))
                        .out()
                return result
            } else {
                throw ErrorException(ERROR_CODE_SIGNATURE_INVAILIED, "验签失败")
            }
        } else {
            throw ErrorException(code, json.optString("msg"))
        }
    }
}