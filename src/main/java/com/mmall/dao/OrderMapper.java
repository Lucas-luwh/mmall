package com.mmall.dao;

import com.mmall.pojo.Order;
import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julin
 */
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    /**
     * 选择性更新订单
     * @param record 订单
     * @return 结果
     */
    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 通过用户和订单号查询订单号
     * @param userId 用户id
     * @param orderNo 订单号
     * @return 订单信息
     */
	Order selectByUserIdAndOrderNo(@Param(value = "userId") Integer userId, @Param(value = "orderNo") Long orderNo);

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单
     */
    Order selectByOrderNo(@Param("orderNo") long orderNo);

    /**
     * 批量插入
     * @param orderItemList 订单
     */
	void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);

	/**
	 * 查询当前用户的全部订单
	 * @param id 用户id
	 * @return 订单信息
	 */
	List<Order> selectByUserId(@Param("userId") Integer id);

	/**
	 * 查询所有订单
	 * @return 订单集合
	 */
	List<Order> selectAllOrder();
}