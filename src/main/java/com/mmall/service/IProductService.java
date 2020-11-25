package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/11/23:22:07
 * @version: 1.0
 */
public interface IProductService {

	/**
	 * 添加或更新产品
	 * @param product 产品
	 * @return 响应信息
	 */
	ServerResponse<String> saveOrUpdateProduct(Product product);

	/**
	 * 设置产品的销售状态
	 * @param productId 产品id
	 * @param status 状态
	 * @return 响应信息
	 */
	ServerResponse<String> setSaleStatus(Integer productId,Integer status);

	/**
	 * 查看产品的详细信息
	 * @param productId 产品id
	 * @return 封装的实体
	 */
	ServerResponse<ProductDetailVo>  manageProductDetail(Integer productId);
}
