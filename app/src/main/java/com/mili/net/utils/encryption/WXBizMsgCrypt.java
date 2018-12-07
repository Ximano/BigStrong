/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package com.mili.net.utils.encryption;

//import org.apache.commons.codec.binary.Base64;

import android.util.Base64;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串).
 * <ol>
 * 	<li>第三方回复加密消息给公众平台</li>
 * 	<li>第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * 	<li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 *      http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 * 	<li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * 	<li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * 	<li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class WXBizMsgCrypt {
	static Charset CHARSET = Charset.forName("utf-8");
	//Base64 base64 = new Base64();
	byte[] aesKey;
	String token;
	String appId;

	/**
	 * 构造函数
	 * @param token 公众平台上，开发者设置的token
	 * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
	 * @param appId 公众平台appid
	 * 
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public WXBizMsgCrypt(String token, String encodingAesKey, String appId) throws AesException {

		this.token = token;
		this.appId = appId;
		//aesKey = Base64.decode(encodingAesKey + "=", Base64.DEFAULT);
		aesKey = encodingAesKey.getBytes();
	}

	// 随机生成16位字符串
	public String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 对明文进行加密.
	 * 
	 * @param text 需要加密的明文
	 * @return 加密后base64编码的字符串
	 * @throws AesException aes加密失败
	 */
	public String encrypt(String randomStr, String text) throws AesException {
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		//byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		byte[] appidBytes = appId.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + appid
		byteCollector.addBytes(randomStrBytes);
		//byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		byteCollector.addBytes(appidBytes);

		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		//byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		//byteCollector.addBytes(padBytes);

		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();

		try {
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);

			// 使用BASE64对加密后的字符串进行编码
			String base64Encrypted = Base64.encodeToString(encrypted, Base64.NO_WRAP);

			return base64Encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.EncryptAESError);
		}
	}

	/**
	 * 对密文进行解密.
	 * 
	 * @param text 需要解密的密文
	 * @return 解密得到的明文
	 * @throws AesException aes解密失败
	 */
	public String decrypt(String text) throws AesException {
		byte[] original;
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decode(text, Base64.NO_WRAP);

			// 解密
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.DecryptAESError);
		}

		String content, from_appid;
		try {
			// 去除补位字符
			//byte[] bytes = PKCS7Encoder.decode(original);

			byte[] bytes = original;

			// 分离16位随机字符串和AppId
			//byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

			// appid=android 所以除去16到-7位得到正文
			Integer len = bytes.length;
			content = new String(Arrays.copyOfRange(bytes, 16, len - 7), CHARSET);
			//from_appid = new String(Arrays.copyOfRange(bytes, -7), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.IllegalBuffer);
		}

		// appid不相同的情况
		// if (!from_appid.equals(appId)) {
		// 	throw new AesException(AesException.ValidateAppidError);
		// }
		return content;
	}

	/**
	 * 生成安全签名.
	 * 
	 * @param encrypt 经过加密的字符串
	 * @param timeStamp 时间戳, 可以自己生成,也可以使用返回参数
	 * @param nonce 干扰码,可以自己生成,也可以使用返回参数
	 * @return 签名
	 */
	public String getSignature(String encrypt, String timeStamp, String nonce) {
		// 生成安全签名
		if (timeStamp == "") {
			timeStamp = Long.toString(System.currentTimeMillis());
		}
		try {

			String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);
			return signature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
