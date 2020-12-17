package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @package: com.mmall.controller.portal
 * @author: luweihong
 * @description:
 * @create: 2020/12/15 20:14
 * @version: 1.0
 */
@Controller
@RequestMapping("/order/")
@Slf4j
public class OrderController {

	@Autowired
	private IOrderService iOrderService;

	public ServerResponse pay(HttpSession session,Long orderNo, HttpServletRequest request){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		String path = request.getSession().getServletContext().getRealPath("upload");
		return iOrderService.pay(orderNo,user.getId(),path);
	}

	public ServerResponse create(HttpSession session, Integer shippingId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return null;
	}
}
