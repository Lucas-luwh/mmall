package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julin
 */
public interface CartMapper {
    /**
     * 清空购物车
     * @param id 购物车id
     * @return 结果信息
     */
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

    /**
     * 更新购物车
     * @param record 购物车
     * @return 数量
     */
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

    /**
     * 删除购物车中的产品
     * @param userId 用户id
     * @param productIdList 产品id集合
     * @return 删除数量
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIdList")List<String> productIdList);

    /**
     * 检查产品的状态
     * @param userId 用户id
     * @param productId 产品id
     * @param checked 选择状态
     * @return 更新条数
     */
    int checkedOrUncheckedProduct(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked") Integer checked);

    /**
     * 查询当前用户的购物车数量
     * @param userId 用户id
     * @return 结果信息
     */
    int selectCartProductCount(@Param("userId") Integer userId);

    /**
     * 通过用户id查询选中的的购物
     * @param userId 用户id
     * @return 购物车
     */
	List<Cart> selectCheckedCartByUserId(@Param("userId") Integer userId);
}