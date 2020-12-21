package com.mmall.dao;

import com.mmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

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
}