package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @package: com.mmall.vo
 * @author: luweihong
 * @description:
 * @create: 2020/12/22 21:31
 * @version: 1.0
 */
@Data
public class OrderVo {
	private Long orderNo;

	private BigDecimal payment;

	private Integer paymentType;

	private String paymentTypeDesc;
	private Integer postage;

	private Integer status;


	private String statusDesc;

	private String paymentTime;

	private String sendTime;

	private String endTime;

	private String closeTime;

	private String createTime;

	//订单的明细
	private List<OrderItemVo> orderItemVoList;

	private String imageHost;
	private Integer shippingId;
	private String receiverName;

	private ShippingVo shippingVo;

}
