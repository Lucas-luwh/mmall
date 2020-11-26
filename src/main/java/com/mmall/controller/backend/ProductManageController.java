package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @package: com.mmall.controller.backend
 * @author: luweihong
 * @description:
 * @create: 2020/11/23:21:58
 * @version: 1.0
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

	@Autowired
	private IUserService iUserService;
	@Autowired
	private IProductService iProductService;

	@RequestMapping("save.do")
	@ResponseBody
	public ServerResponse<String> productSave(HttpSession session, Product product){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要进行登录");
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.saveOrUpdateProduct(product);
		}
		return ServerResponse.createByErrorMessage("无权限进行操作");
	}

	@RequestMapping("set_sale_status.do")
	@ResponseBody
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId,Integer status){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要进行登录");
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.setSaleStatus(productId,status);
		}
		return ServerResponse.createByErrorMessage("无权限进行操作");
	}

	@RequestMapping("detail.do")
	@ResponseBody
	public ServerResponse<ProductDetailVo> getDetail(HttpSession session, Integer productId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要进行登录");
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.manageProductDetail(productId);
		}
		return ServerResponse.createByErrorMessage("无权限进行操作");
	}

	@RequestMapping("list.do")
	@ResponseBody
	public ServerResponse<PageInfo<ProductListVo>> getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要进行登录");
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.getProductList(pageNum,pageSize);
		}
		return ServerResponse.createByErrorMessage("无权限进行操作");
	}
}
