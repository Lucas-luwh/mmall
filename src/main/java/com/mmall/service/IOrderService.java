package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/12/15 20:45
 * @version: 1.0
 */
public interface IOrderService {
	/**
	 * 支付
	 * @param orderNo 订单号
	 * @param userId 用户id
	 * @param path 路径
	 * @return 订单信息
	 */
	ServerResponse pay(Long orderNo, Integer userId, String path);
}
