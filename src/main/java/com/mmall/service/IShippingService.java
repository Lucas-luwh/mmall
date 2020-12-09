package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

import java.util.HashMap;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/12/9 21:49
 * @version: 1.0
 */
public interface IShippingService {

	/**
	 * 新增地址信息
	 * @param userId 用户id
	 * @param shipping 购物详情
	 * @return 信息
	 */
	ServerResponse<HashMap<String,Object>> add(Integer userId, Shipping shipping);

	/**
	 * 删除地址信息
	 * @param userId 用户id
	 * @param shippingId id
	 * @return 数量
	 */
	ServerResponse<String> del(Integer userId,Integer shippingId);

	/**
	 * 更新地址信息
	 * @param userId 用户id
	 * @param shipping 地址信息
	 * @return 响应信息
	 */
	ServerResponse<String> update(Integer userId, Shipping shipping);

	/**
	 * 查询收获地址
	 * @param userId 用户id
	 * @param shippingId id
	 * @return 地址详情
	 */
	ServerResponse<Shipping> select(Integer userId, Integer shippingId);
}
