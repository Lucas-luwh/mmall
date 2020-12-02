package com.mmall.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @package: com.mmall.util
 * @author: luweihong
 * @description: 数值计算工具类
 * @create: 2020/12/2 22:55
 * @version: 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtil {

	public static BigDecimal add(double v1, double v2){
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.add(b2);
	}

	public static BigDecimal sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.subtract(b2);
	}


	public static BigDecimal mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.multiply(b2);
	}

	public static BigDecimal div(double v1,double v2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		//四舍五入,保留2位小数
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
	}

}
