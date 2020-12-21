package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

	@RequestMapping("pay.do")
	@ResponseBody
	public ServerResponse<HashMap<String,String>> pay(HttpSession session, Long orderNo, HttpServletRequest request){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		String path = request.getSession().getServletContext().getRealPath("upload");
		return iOrderService.pay(orderNo,user.getId(),path);
	}

	@RequestMapping("alipay_callback.do")
	@ResponseBody
	public Object alipayCallback(HttpServletRequest request){
		HashMap<String, String> params = Maps.newHashMap();

		Map<String, String[]> requestParams = request.getParameterMap();
		for (String name : requestParams.keySet()) {
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		log.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

		//非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.
		params.remove("sign_type");

		try {
			boolean rsaCheckV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
			if (!rsaCheckV2){
				return ServerResponse.createByErrorMessage("非法请求,验证不通过,再恶意请求我就报警找网警了");
			}
		} catch (AlipayApiException e) {
			log.error("支付宝验证回调异常",e);
		}

		ServerResponse<Integer> serverResponse = iOrderService.aliCallback(params);
		if (serverResponse.isSuccess()){
			return Const.AlipayCallback.RESPONSE_SUCCESS;
		}
		return Const.AlipayCallback.RESPONSE_FAILED;
	}

	@RequestMapping("query_order_pay_status.do")
	@ResponseBody
	public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}

		ServerResponse<String> serverResponse = iOrderService.queryOrderPayStatus(user.getId(),orderNo);
		return serverResponse.isSuccess() ? ServerResponse.createBySuccess(true) : ServerResponse.createBySuccess(false);
	}

	public ServerResponse create(HttpSession session, Integer shippingId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user ==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		return null;
	}
}
