package com.jaqen.buhuaxin.net.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mili.net.utils.EncryptionUtil
import com.mili.net.converter.EncryptRequestBodyConverter
import com.mili.net.converter.EncryptResponseBodyConverter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class EncryptConverterFactory private constructor(val gson: Gson): Converter.Factory() {
    companion object {

        @JvmStatic
        public fun create(): EncryptConverterFactory{
            return create(GsonBuilder().setLenient().create())
        }

        @JvmStatic
        public fun create(gson: Gson): EncryptConverterFactory{
            return EncryptConverterFactory(gson)
        }
    }

    private val encryptUtil = EncryptionUtil()

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        /*val newType = object : ParameterizedType{
            override fun getOwnerType(): Type? {
                return null
            }

            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(type)
            }

            override fun getRawType(): Type {
                return APIData::class.java
            }

        }*/
        val adapter = gson.getAdapter(TypeToken.get(type))

        return EncryptResponseBodyConverter(type, gson, adapter, encryptUtil)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return EncryptRequestBodyConverter(gson, adapter, encryptUtil)
    }
}