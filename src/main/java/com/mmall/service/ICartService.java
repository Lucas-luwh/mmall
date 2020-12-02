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
}
