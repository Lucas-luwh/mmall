package com.mmall.controller.portal;

import com.mmall.common.CartImpl;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @package: com.mmall.controller.portal
 * @author: luweihong
 * @description:
 * @create: 2020/12/2:21:23
 * @version: 1.0
 */
@Controller
@RequestMapping("/cart/")
public class CartController {

	@Autowired
	private ICartService iCartService;

	@RequestMapping("list.do")
	@ResponseBody
	public ServerResponse<CartVo> list(HttpSession session){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.list(user.getId());
	}

	@RequestMapping("add.do")
	@ResponseBody
	public ServerResponse<CartVo> add(HttpSession session,Integer count,Integer productId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.add(user.getId(),count,productId);
	}

	@RequestMapping("update.do")
	@ResponseBody
	public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.update(user.getId(),productId,count);
	}

	@RequestMapping("delete_product.do")
	@ResponseBody
	public ServerResponse<CartVo> deleteProduct(HttpSession session,String productIds){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return  iCartService.deleteProduct(user.getId(),productIds);
	}

	@RequestMapping("select_all.do")
	@ResponseBody
	public ServerResponse<CartVo> selectAll(HttpSession session){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.selectOrUnSelect(user.getId(),null, CartImpl.CHECKED);
	}

	@RequestMapping("un_select_all.do")
	@ResponseBody
	public ServerResponse<CartVo> unSelectAll(HttpSession session){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.selectOrUnSelect(user.getId(),null,CartImpl.UN_CHECKED);
	}

	@RequestMapping("select.do")
	@ResponseBody
	public ServerResponse<CartVo> select(HttpSession session,Integer productId){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.selectOrUnSelect(user.getId(),productId,CartImpl.CHECKED);
	}

	@RequestMapping("un_select.do")
	@ResponseBody
	public ServerResponse<CartVo> unSelect(HttpSession session,Integer productId){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return iCartService.selectOrUnSelect(user.getId(),productId,CartImpl.UN_CHECKED);
	}

	@RequestMapping("get_cart_product_count.do")
	@ResponseBody
	public ServerResponse<Integer> getCartProductCount(HttpSession session){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createBySuccess(0);
		}
		return iCartService.getCartProductCount(user.getId());
	}
}
