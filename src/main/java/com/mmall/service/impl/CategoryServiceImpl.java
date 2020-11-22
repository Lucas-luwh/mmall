package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description:
 * @create: 2020/11/21:14:53
 * @version: 1.0
 */
@Service("iCategoryService")
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public ServerResponse<String> addCategory(String categoryName, Integer parentId){
		//判断参数是否有值
		if (parentId == null || StringUtils.isBlank(categoryName)){
			return ServerResponse.createByErrorMessage("添加参数有误");
		}

		Category category = new Category();
		category.setParentId(parentId);
		category.setName(categoryName);
		category.setStatus(true);
		int count = categoryMapper.insert(category);

		if (count > 0){
			return ServerResponse.createBySuccessMessage("添加品类成功");
		}
		return ServerResponse.createByErrorMessage("添加品类失败");
	}

	@Override
	public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName){
		//判断参数是否有值
		if (categoryId == null || StringUtils.isBlank(categoryName)){
			return ServerResponse.createByErrorMessage("添加参数有误");
		}

		Category category = new Category();
		category.setId(categoryId);
		category.setName(categoryName);

		int count = categoryMapper.updateByPrimaryKeySelective(category);
		if (count > 0){
			return ServerResponse.createBySuccessMessage("更新分类名称成功");
		}
		return ServerResponse.createByErrorMessage("更新分类名称失败");
	}

	@Override
	public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
		List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);
		if (CollectionUtils.isEmpty(categories)){
			log.info("未找到当前分类的子分类");
		}
		return ServerResponse.createBySuccess(categories);
	}
}
