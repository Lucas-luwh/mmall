package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.HashMap;

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
	ServerResponse<HashMap<String,String>> pay(Long orderNo, Integer userId, String path);

	/**
	 * 回调验证参数
	 * @param params 参数
	 * @return 响应信息
	 */
	ServerResponse<Integer> aliCallback(HashMap<String, String> params);

	/**
	 * 查询订单支付状态
	 * @param id 序号
	 * @param orderNo 订单号
	 * @return 响应信息
	 */
	ServerResponse<String> queryOrderPayStatus(Integer id, Long orderNo);
}
