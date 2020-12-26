package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.HashMap;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description: 订单服务
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

	/**
	 * 创建订单
	 * @param id 用户id
	 * @param shippingId 地址id
	 * @return 响应信息
	 */
	ServerResponse<String> createOrder(Integer id, Integer shippingId);

	/**
	 * 取消订单
	 * @param id 用户id
	 * @param orderNo 订单号
	 * @return 响应信息
	 */
	ServerResponse<String> cancel(Integer id, Long orderNo);

	/**
	 * 获取订单中产品的内容
	 * @param id 用户id
	 * @return 信息
	 */
	ServerResponse<Object> getOrderCartProduct(Integer id);

	/**
	 * 获取订单详情
	 * @param id 用户id
	 * @param orderNo 订单号
	 * @return 信息
	 */
	ServerResponse<OrderVo> getOrderDetail(Integer id, Long orderNo);

	/**
	 * 获取全部订单信息
	 * @param id 用户id
	 * @param pageNum 页面
	 * @param pageSize 页数
	 * @return 信息
	 */
	ServerResponse getOrderList(Integer id, int pageNum, int pageSize);

	/**
	 * 展示订单列表
	 * @param pageNum pageNum
	 * @param pageSize pageSize
	 * @return 信息
	 */
	ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

	/**
	 * 详细订单信息
	 * @param orderNo 订单号
	 * @return  OrderVo
	 */
	ServerResponse<OrderVo> manageDetail(Long orderNo);

	/**
	 * 搜索
	 * @param orderNo 订单号
	 * @param pageNum pageNum
	 * @param pageSize pageSize
	 * @return PageInfo
	 */
	ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

	/**
	 * 发货信息
	 * @param orderNo 订单号
	 * @return 信息
	 */
	ServerResponse<String> manageSendGoods(Long orderNo);
}
