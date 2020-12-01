package com.mmall.common;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

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

	public static final String SUCCESS = "success";
	/**
	 * 设置用户权限
	 */
	public interface Role{
		//普通用户
		int ROLE_CUSTOMER = 0;
		//管理员
		int ROLE_ADMIN = 1;
	}

	public interface ProductListOrderBy{
		//用contain方法复杂度为o(1)
		Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
	}

	/**
	 * 产品状态信息
	 */
	@Getter
	public enum ProductStatusEnum{
		/**
		 * 状态
		 */
		ON_SALE(1,"在线");
		/**
		 * 值
		 */
		private String value;
		/**
		 * 响应码
		 */
		private int code;
		ProductStatusEnum(int code,String value){
			this.code = code;
			this.value = value;
		}
	}
}
