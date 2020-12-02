package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @package: com.mmall.vo
 * @author: luweihong
 * @description:
 * @create: 2020/12/2:21:25
 * @version: 1.0
 */
@Data
public class CartVo {

	private List<CartProductVo> cartProductVoList;
	/**
	 * 购物车商品里的总价
	 */
	private BigDecimal cartTotalPrice;
	/**
	 * 是否被选中
	 */
	private Boolean allChecked;
	private String imageHost;
}
