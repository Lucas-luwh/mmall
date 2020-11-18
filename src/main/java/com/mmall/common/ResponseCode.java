package com.mmall.common;

import lombok.Getter;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description: 响应代码封装
 * @create: 2020/11/15:16:34
 * @version: 1.0
 */
@Getter
public enum ResponseCode {

	/**
	 * 成功
	 */
	SUCCESS(200,"SUCCESS"),
	/**
	 * 错误
	 */
	ERROR(1,"ERROR"),
	/**
	 * 需要登录
	 */
	NEED_LOGIN(10,"NEED_LOGIN"),
	/**
	 * 参数错误
	 */
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

	private final int code;
	private final String desc;

	ResponseCode(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
}
