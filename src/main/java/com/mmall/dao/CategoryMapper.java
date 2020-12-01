package com.mmall.dao;

import com.mmall.pojo.Category;

import java.util.List;

/**
 * @author julin
 */
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加品种信息
     * @param record 品种
     * @return 响应信息
     */
    int insert(Category record);

    int insertSelective(Category record);

    /**
     * 通过id查看分类信息
     * @param id 分类id
     * @return 品种信息
     */
    Category selectByPrimaryKey(Integer id);

    /**
     * 选择性更新分类信息
     * @param record 分类
     * @return 数量
     */
    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * 获取当前节点下的子节点所有同级分类信息
     * @param parentId 父级id
     * @return 集合
     */
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}