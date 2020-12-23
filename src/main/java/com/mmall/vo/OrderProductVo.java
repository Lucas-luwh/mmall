package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @package: com.mmall.vo
 * @author: luweihong
 * @description:
 * @create: 2020/12/23 22:54
 * @version: 1.0
 */
@Data
public class OrderProductVo {
	private List<OrderItemVo> orderItemVoList;
	private BigDecimal productTotalPrice;
	private String imageHost;
}
