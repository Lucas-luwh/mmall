package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/11/21:14:53
 * @version: 1.0
 */
public interface ICategoryService {

	/**
	 * 添加品类
	 * @param categoryName 品类名称
	 * @param parentId 父级id
	 * @return 响应信息
	 */
	ServerResponse<String> addCategory(String categoryName, Integer parentId);

	/**
	 * 更新分类名称
	 * @param categoryId 分类id
	 * @param categoryName 分类名称
	 * @return 响应信息
	 */
	ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);

	/**
	 * 获取当前节点下的子节点所有同级分类信息
	 * @param categoryId 分类id
	 * @return 集合
	 */
	ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

	/**
	 * 递归查询本节点的id及孩子节点的id
	 * @param categoryId 节点
	 * @return 集合
	 */
	ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
