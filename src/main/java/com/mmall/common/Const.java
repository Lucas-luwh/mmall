package com.mmall.common;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description: 常量类
 * @create: 2020/11/15:17:51
 * @version: 1.0
 */
public class Const {

	/**
	 * 当前用户
	 */
	public static final String CURRENT_USER = "currentUser";

	public static final String EMAIL ="email";

	public static final String USERNAME = "userName";
	/**
	 * 设置用户权限
	 */
	public interface Role{
		//普通用户
		int ROLE_CUSTOMER = 0;
		//管理员
		int ROLE_ADMIN = 1;
	}

}
