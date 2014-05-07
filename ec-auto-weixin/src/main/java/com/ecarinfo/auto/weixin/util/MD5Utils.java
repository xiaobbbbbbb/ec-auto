package com.ecarinfo.auto.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Description:加密工具类
 * @Description:md5加密出来的长度是32位
 * @Description:sha加密出来的长度是40位
 * @Author Dawn
 * @Date 2012-11-6
 * @Version V1.0
 */

public class MD5Utils {
	/**
	 * @Description:加密
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}

	/**
	 * @Description:二次加密
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * @Description:md5加密
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * @Description:sha加密
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * @Description:md5或者sha-1加密
	 * @param: inputText 要加密的内容
	 * @param: algorithmName 加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * @Description:返回十六进制字符串
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	/**
	 * @Description:md5 解密(相对于MD5方法)
	 * @param paramString
	 * @return
	 */
	public static String unMD5(String paramString) {
		BASE64Decoder localBASE64Decoder = new BASE64Decoder();
		try {
			return new String(localBASE64Decoder.decodeBuffer(paramString));
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	/**
	 * @Description:md5 加密
	 * @param paramString
	 * @return
	 */
	public static String onMD5(String paramString) {
		byte[] arrayOfByte1 = paramString.getBytes();
		byte[] arrayOfByte2 = new byte[32];
		int i = arrayOfByte1.length;
		if (i > 31)
			i = 31;
		arrayOfByte2[0] = (byte) i;
		BASE64Encoder localBASE64Encoder = new BASE64Encoder();
		try {
			return localBASE64Encoder.encode(paramString.getBytes());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(MD5Utils.md5AndSha("123456"));
	}
}
