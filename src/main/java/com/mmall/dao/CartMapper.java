package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julin
 */
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增购物车记录
     * @param record 购物车信息
     * @return 插入数量
     */
    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    /**
     * 更新购物车信息
     * @param record 购物车信息
     * @return 更新状态
     */
    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    /**
     * 通过用户id和产品id查看购物车信息
     * @param userId 用户id
     * @param productId 产品id
     * @return 购物车信息
     */
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId")Integer productId);

    /**
     * 通过用户id查找所有购物车信息
     * @param userId 用户id
     * @return 集合
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 查看状态
     * @param userId 用户id
     * @return 数目
     */
    int selectCartProductCheckedStatusByUserId(Integer userId);
}