package com.mmall.common;

import lombok.Getter;

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
	}

	public interface ProductListOrderBy{
	}

	public interface Cart{
	}

	public interface AlipayCallback{
		String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
		String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
		String RESPONSE_SUCCESS = "success";
		String RESPONSE_FAILED = "failed";
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

	public enum OrderStatusEnum{
		/**
		 * 取消
		 */
		CANCELED(0,"已取消"),
		NO_PAY(10,"未支付"),
		PAID(20,"已付款"),
		SHIPPED(40,"已发货"),
		ORDER_SUCCESS(50,"订单完成"),
		ORDER_CLOSE(60,"订单关闭");


		OrderStatusEnum(int code,String value){
			this.code = code;
			this.value = value;
		}
		private String value;
		private int code;

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}

		public static OrderStatusEnum codeOf(int code) throws RuntimeException {
			for(OrderStatusEnum orderStatusEnum : values()){
				if(orderStatusEnum.getCode() == code){
					return orderStatusEnum;
				}
			}
			throw new RuntimeException("么有找到对应的枚举");
		}
	}

	public enum PayPlatformEnum{
		ALIPAY(1,"支付宝");

		PayPlatformEnum(int code,String value){
			this.code = code;
			this.value = value;
		}
		private String value;
		private int code;

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}
	}
}
