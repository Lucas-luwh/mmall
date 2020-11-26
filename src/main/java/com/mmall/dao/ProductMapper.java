package com.mmall.dao;

import com.mmall.pojo.Product;

import java.util.List;

/**
 * @author julin
 */
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增产品
     * @param record 产品
     * @return 数目
     */
    int insert(Product record);

    int insertSelective(Product record);

    /**
     * 查看产品详情
     * @param id 产品id
     * @return 产品信息
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 通过条件进行更新产品
     * @param record 产品
     * @return 数目
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 通过id更新产品属性
     * @param record 产品
     * @return 数目
     */
    int updateByPrimaryKey(Product record);

    /**
     * 查询所有产品
     * @return 集合
     */
    List<Product> selectList();
}