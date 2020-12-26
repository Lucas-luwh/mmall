package com.mmall.dao;

import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julin
 */
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    /**
     * 通过用户id和订单号获取订单子项
     * @param orderNo 订单号
     * @param userId 用户id
     * @return 集合
     */
	List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    /**
     * 获取订单子项
     * @param orderNo 订单号
     */
    List<OrderItem> getByOrderNo(@Param("orderNo") Long orderNo);
}