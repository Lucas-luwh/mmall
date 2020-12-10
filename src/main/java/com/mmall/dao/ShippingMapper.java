package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julin
 */
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加收获地址
     * @param record 购物
     * @return 数量
     */
    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * 通过id删除地址信息
     * @param userId 用户id
     * @param shippingId id
     * @return 数量
     */
    int deleteByShippingIdUserId(@Param("userId") Integer userId,@Param("shippingId") Integer shippingId);

    /**
     * 更新地址信息
     * @param shipping 地址信息
     * @return 更新数量
     */
    int updateByShipping(Shipping shipping);

    /**
     * 通过id查询地址
     * @param userId 用户id
     * @param shippingId id
     * @return 地址详情
     */
    Shipping selectByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 通过用户id查看地址信息
     * @param userId 用户id
     * @return 集合信息
     */
    List<Shipping> selectByUserId(Integer userId);
}