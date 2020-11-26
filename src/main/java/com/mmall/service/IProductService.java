package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;

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

	/**
	 * 查询所有产品
	 * @param pageNum 页数
	 * @param pageSize 一页数量
	 * @return 分页展示信息
	 */
	ServerResponse<PageInfo<ProductListVo>> getProductList(int pageNum, int pageSize);
}
