package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ErrorConst;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @package: com.mmall.controller.backend
 * @author: luweihong
 * @description:
 * @create: 2020/11/21:14:37
 * @version: 1.0
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

	@Autowired
	private IUserService iUserService;
	@Autowired
	private ICategoryService iCategoryService;

	@RequestMapping("add_category.do")
	@ResponseBody
	public ServerResponse<String> addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
		//先判断用户是否登录
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ErrorConst.NEED_LOGIN_MESSAGE);
		}

		//判断用户是否为管理员权限
		ServerResponse<String> response = iUserService.checkAdminRole(user);
		if (response.isSuccess()){
			return iCategoryService.addCategory(categoryName,parentId);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_ADMIN_LOGIN);
	}

	@RequestMapping("set_category_name.do")
	@ResponseBody
	public ServerResponse<String> setCategoryName(HttpSession session,Integer categoryId,String categoryName){
		//先判断用户是否登录
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		//判断用户是否为管理员权限
		ServerResponse<String> response = iUserService.checkAdminRole(user);
		if (response.isSuccess()){
			return iCategoryService.updateCategoryName(categoryId,categoryName);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_ADMIN_LOGIN);
	}

	@RequestMapping("get_category.do")
	@ResponseBody
	public ServerResponse<List<Category>> getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
		//先判断用户是否登录
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		//判断用户是否为管理员权限
		ServerResponse<String> response = iUserService.checkAdminRole(user);
		if (response.isSuccess()){
			return iCategoryService.getChildrenParallelCategory(categoryId);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_ADMIN_LOGIN);
	}
}
