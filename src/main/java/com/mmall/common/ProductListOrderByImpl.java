package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description:
 * @create: 2020/12/2 22:15
 * @version: 1.0
 */
public class ProductListOrderByImpl implements Const.ProductListOrderBy {

	/**
	 * 用set contain方法复杂度为o(1)
	 */
	protected static final Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
}
