package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @package: com.mmall.vo
 * @author: luweihong
 * @description:
 * @create: 2020/12/22 21:33
 * @version: 1.0
 */
@Data
public class OrderItemVo {

	private Long orderNo;

	private Integer productId;

	private String productName;
	private String productImage;

	private BigDecimal currentUnitPrice;

	private Integer quantity;

	private BigDecimal totalPrice;

	private String createTime;
}
