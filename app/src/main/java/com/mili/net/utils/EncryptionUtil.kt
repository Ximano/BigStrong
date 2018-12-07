package com.mili.net.utils

import com.lazy.library.logging.Logcat
import com.mili.net.utils.encryption.WXBizMsgCrypt
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/*
约定参数值
APPID = APPID
KEY = KEY
TOKEN = TOKEN

1加密
1.1 拼接字符串
TEXT = [长度16的随机字符串] + [长度为4网络字节序 打包内容为明文json的长度] + [明文json] + APPID
1.2 补位
Size为32
TEXT = PKCS7encode(TEXT)
1.3 开始加密
IV = SUBSTR(KEY, 0,16) // 取key的前16位为IV值
DATA = ENCRYPT(‘AES-256-CBC’, KEY,IV)
DATA = BASE64ENCODE(DATA)

2解密

3 验签
SIGNATURE = 字符串升序排列(密文data, timestamp,TOKEN,nonce) // 排序后的字符串
SIGNATURE = sha1(SIGNATURE)
*/
class EncryptionUtil(private val token: String = TOKEN, val aesKey: String = AES_KEY, private val appId: String = APPID) {
    companion object {
        const val TOKEN = "nv9yimMA"
        const val AES_KEY = "bbd8ac472ae8498de3638daa9dad6e72"
        const val APPID = "android"
        const val BLOCK_SIZE = 32
        @JvmStatic
        val CHARSET = Charsets.UTF_8
    }

    public data class EncryptedData(val data: String, val timeStamp: String, val nonce: String, val signature: String)
    public data class DecryptedData(val data: String)

    private val noceSize = 16
    private val stringRange = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    //指定算法，模式，填充方式，创建一个Cipher
    //private val encryptCipher = Cipher.getInstance("AES/CBC/NoPadding")
    private val encryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
    private val decryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
    private val bizCript = WXBizMsgCrypt(token, aesKey, appId)
    //private val crypt: WXBizMsgCrypt

    init {
        //导入支持AES/CBC/PKCS7Padding的Provider
        /*Security.addProvider(BouncyCastleProvider())

        //生成Key对象
        val sKeySpec = createKeySpec(aesKey)

        //把向量初始化到算法参数
        val params = AlgorithmParameters.getInstance("AES")
        params.init(IvParameterSpec(aesKey.substring(0 until 16).toByteArray(Charsets.UTF_8)))

        encryptCipher.init(Cipher.ENCRYPT_MODE, sKeySpec, params)
        decryptCipher.init(Cipher.DECRYPT_MODE, sKeySpec, params)*/

        //crypt = WXBizMsgCrypt(token, aesKey, appId)
    }

    /**
     * 执行加密
     */
    fun encrypt(data: String): EncryptedData {

        /*val nonce = createRandomString16(noceSize)
        val textBytes = packTextBytes(data, nonce)
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        //指定加密
        val paddingPKCS7 = encodePKCS7(textBytes)
        //val beforeEncrypt = Base64.encode(textBytes, Base64.NO_WRAP)
        val result = encryptCipher.doFinal(textBytes)
        val finalResult = Base64.encodeToString(result, Base64.NO_WRAP)
        val signature = createSignature(finalResult, timeStamp, nonce)*/

        /*val temp1 = Base64.decode(finalResult, Base64.NO_WRAP)
        val temp2 = decryptCipher.doFinal(temp1)
        val temp3 = decodePKCS7(temp2)
        val temp4 = recoverNetworkBytesOrder(temp3.sliceArray(16 until 20))
        val temp5 = String(temp3.sliceArray(20 until 20+temp4), CHARSET)*/
        val nonce = bizCript.randomStr
        val result = bizCript.encrypt(nonce, data)
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val signature = bizCript.getSignature(result, timeStamp, nonce)

        Logcat.d().ln()
                .format("jsonParam: %s", data).ln()
                .out()

        return EncryptedData(result, timeStamp, nonce, signature)
    }

    /**
     * 执行解密
     */
    fun decrypt(data: String): DecryptedData {

        /*var aesData = Base64.decode(data, Base64.NO_WRAP)
        //执行解密
        val textBytes = decryptCipher.doFinal(aesData)
        //aesData = Base64.decode(textBytes, Base64.NO_WRAP)
        val finalData = analysisText(textBytes)*/
        val result = bizCript.decrypt(data)

        return DecryptedData(result)
    }

    fun verSignature(signature: String, data: String, nonce: String, timeStamp: String): Boolean {
        /*val newSignature = createSignature(data, timeStamp, nonce)

        return newSignature == signature*/

        return true
    }

    private fun createKeySpec(key: String): SecretKeySpec {
        // 如果为128将长度改为128即可
        val length = 256 / 8
        val keyBytes = key.toByteArray(Charsets.UTF_8)
        val finalKeyBytes = ByteArray(length) { if (it < keyBytes.size) keyBytes[it] else 0x0 }
        //val fillLength = if (keyBytes.size < length) keyBytes.size else length

        return SecretKeySpec(finalKeyBytes, "AES")
    }

    /**
     * 拼接字符串
     */
    private fun packTextBytes(data: String, nonce: String): ByteArray {

        var textBytes = nonce.toByteArray(CHARSET)

        //textBytes = textBytes.plus(getNetworkBytesOrder(data.toByteArray().size))
        textBytes = textBytes.plus(data.toByteArray(CHARSET))
        textBytes = textBytes.plus(appId.toByteArray(CHARSET))
        /*for (i in 0..3){
            val value = Integer.toBinaryString(jsonSize shr (i * 8) and 0xff)

            for (bu in 0 until 8 - value.length) text += "0"
            text += value
        }
        text += data
        text += appId
        return text*/
        Logcat.d().tag("加密前数据").ln().msg(String(textBytes)).out()
        return textBytes
    }

    /**
     * 解析字符串
     */
    private fun analysisText(textBytes: ByteArray): String {
        //val jsonLength = recoverNetworkBytesOrder(textBytes.sliceArray(16 until 20))

        val origin = String(textBytes, CHARSET)
        val noPrefix = origin.removeRange(0 until 16)

        return noPrefix.substringBeforeLast(appId)
        //return origin.slice(20 until (20 + jsonLength))
    }

    /**
     * 创建随机的16位字符串
     */
    private fun createRandomString16(length: Int): String {
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length) {
            val number = random.nextInt(stringRange.length)
            sb.append(stringRange[number])
        }
        //return sb.toString()
        return "AAAAAAAAAAAAAAAA"
    }

    /**
     * 创建Signature将给定参数组合升序排列后sha1
     */
    public fun createSignature(data: String, timeStamp: String, nonce: String): String {
        val values = arrayListOf(data, timeStamp, token, nonce)
        val md5 = MessageDigest.getInstance("SHA-1")
        var signature = ""

        values.sortWith(String.CASE_INSENSITIVE_ORDER)
        for (value in values) {
            signature += value
        }

        Logcat.d().ln().format("未加密signature: %s", signature).out()

        md5.update(signature.toByteArray(Charsets.UTF_8))

        return hexString(md5.digest())
    }

    private fun hexString(bytes: ByteArray): String {
        val hexValue = StringBuffer()

        for (i in bytes.indices) {
            val char = bytes[i].toInt() and 0xff
            if (char < 16)
                hexValue.append("0")
            hexValue.append(Integer.toHexString(char))
        }
        return hexValue.toString()
    }

    private fun getNetworkBytesOrder(sourceNumber: Int): ByteArray {
        val orderBytes = ByteArray(4)

        orderBytes[3] = (sourceNumber and 0xFF).toByte()
        orderBytes[2] = (sourceNumber shr 8 and 0xFF).toByte()
        orderBytes[1] = (sourceNumber shr 16 and 0xFF).toByte()
        orderBytes[0] = (sourceNumber shr 24 and 0xFF).toByte()
        return orderBytes
    }

    private fun recoverNetworkBytesOrder(orderBytes: ByteArray): Int {
        var sourceNumber = 0
        for (i in 0..3) {
            sourceNumber = sourceNumber shl 8
            sourceNumber = sourceNumber or (orderBytes[i].toInt() and 0xff)
        }
        return sourceNumber
    }

    fun encodePKCS7(value: ByteArray): ByteArray {
        // 计算需要填充的位数
        var amountToPad = BLOCK_SIZE - value.size % BLOCK_SIZE
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE
        }
        // 获得补位所用的字符
        val padChr = chr(amountToPad)
        var tmp = String()
        for (index in 0 until amountToPad) {
            tmp += padChr
        }
        return value.plus(tmp.toByteArray(CHARSET))
    }

    fun decodePKCS7(decrypted: ByteArray): ByteArray {
        var pad = decrypted[decrypted.size - 1].toInt()
        if (pad < 1 || pad > 32) {
            pad = 0
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.size - pad)
    }

    /**
     * 将数字转化成ASCII码对应的字符，用于对明文进行补码
     *
     * @param a 需要转化的数字
     * @return 转化得到的字符
     */
    fun chr(a: Int): Char {
        val target = (a and 0xFF).toByte()
        return target.toChar()
    }
}