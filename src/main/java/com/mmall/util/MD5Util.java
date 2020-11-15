package com.mmall.util;

import java.security.MessageDigest;

/**
 * @package: com.mmall.util
 * @author: luweihong
 * @description:
 * @create: 2020/11/15:18:13
 * @version: 1.0
 */
public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuilder resultSb = new StringBuilder();
		for (byte value : b) {
			resultSb.append(byteToHexString(value));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 返回大写MD5
	 *
	 * @param origin 密码
	 * @param charsetname 编码格式
	 * @return MD5
	 */
	private static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {

		}
		return resultString.toUpperCase();
	}

	public static String MD5EncodeUtf8(String origin) {
		origin = origin + PropertiesUtil.getProperty("password.salt", "");
		return MD5Encode(origin, "utf-8");
	}


	private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}

