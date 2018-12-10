package com.mili.net.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.lazy.library.logging.Logcat
import com.mili.app.AccessToken
import com.mili.net.bean.UploadRequest
import com.mili.net.utils.EncryptionUtil
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Converter
import java.io.File

class EncryptRequestBodyConverter<T>(private val gson: Gson, val typeAdapter: TypeAdapter<T>, private val encriptionUtil: EncryptionUtil) : Converter<T, RequestBody> {

    override fun convert(value: T): RequestBody {
        if (value is UploadRequest){
            val bodyBuilder = MultipartBody.Builder()
            /*val mediaType = when (value.tob){
                TOB_POSTS_PIC, TOB_AVATAR->"image/"
                TOB_POSTS_VIDEO->"video/mp4"
                else->""
            }*/
            val file = File(value.filePath)
            val fileBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file)
            /*val formBody = FormBody.Builder()
                    .add("tob", value.tob)
                    .add("access_token", AccessToken?:"")
                    .build()
*/
            val fileName = if (file.extension == "album") "${file.nameWithoutExtension}.jpg" else file.name
            bodyBuilder.addFormDataPart("file", fileName, fileBody)
            bodyBuilder.addFormDataPart("tob", value.tob)
            bodyBuilder.addFormDataPart("access_token", AccessToken?:"")
            bodyBuilder.addFormDataPart("suffix", "android")
            bodyBuilder.setType(MultipartBody.FORM)

            return bodyBuilder.build()
        }

        val jsonData = gson.toJson(value)
        val encryptedData = encriptionUtil.encrypt(jsonData)

        val body = FormBody.Builder()
                .add("data", encryptedData.data)
                .add("access_token", AccessToken?:"")
                .add("timestamp", encryptedData.timeStamp)
                .add("nonce", encryptedData.nonce)
                .add("signature", encryptedData.signature)
                .add("suffix", "android")
                .build()

        /*val apiRequestBody = APIRequestBody(encryptedData.data, AccessToken, encryptedData.timeStamp, encryptedData.nonce, encryptedData.signature)
        val jsonBody = gson.toJson(apiRequestBody)*/

        Logcat.d().ln()
                .tag("request")
                .format("data:%s", encryptedData.data).ln()
                .format("access_token:%s", AccessToken).ln()
                .format("timestamp:%s", encryptedData.timeStamp).ln()
                .format("nonce:%s", encryptedData.nonce).ln()
                .format("signature:%s", encryptedData.signature).ln()
                .format("suffix:%s", "android")
                .out()

        //return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), jsonData)
        return body
    }
}