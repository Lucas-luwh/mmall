package com.mmall.common;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description:
 * @create: 2020/12/2 22:19
 * @version: 1.0
 */
public class CartImpl implements Const.Cart{

	/**
	 * 即购物车选中状态
	 */
	public static final int CHECKED = 1;

	/**
	 * 购物车中未选中状态
	 */
	public static final int UN_CHECKED = 0;

	public static final String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
	public static final String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
}
