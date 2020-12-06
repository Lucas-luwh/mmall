package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/12/2 21:50
 * @version: 1.0
 */
public interface ICartService {

	/**
	 * 购物车添加产品
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param count 添加数量
	 * @return 产品vo
	 */
	ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

	/**
	 * 更新购物车
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param count 数量
	 * @return CartVo
	 */
	ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

	/**
	 * 删除购物车中的产品
	 * @param userId 用户id
	 * @param productIds 产品id
	 * @return CartVo
	 */
	ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

	/**
	 * 查看购物车列表
	 * @param userId 用户id
	 * @return CartVo
	 */
	ServerResponse<CartVo> list (Integer userId);

	/**
	 * 查看状态
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param checked 状态
	 * @return CartVo
	 */
	ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);

	/**
	 * 获取购物车的产品的数量
	 * @param userId 用户id
	 * @return 数量
	 */
	ServerResponse<Integer> getCartProductCount(Integer userId);
}
