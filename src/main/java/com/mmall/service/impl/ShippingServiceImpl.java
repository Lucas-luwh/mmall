package com.mmall.service.impl;

import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description:
 * @create: 2020/12/9 21:50
 * @version: 1.0
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

	@Autowired
	private ShippingMapper shippingMapper;

	@Override
	public ServerResponse<HashMap<String,Object>> add(Integer userId, Shipping shipping){
		shipping.setUserId(userId);
		int rowCount = shippingMapper.insert(shipping);
		if (rowCount > 0){
			HashMap<String, Object> result = Maps.newHashMap();
			result.put("shippingId",shipping.getId());
			return ServerResponse.createBySuccess("新建地址成功",result);
		}
		return ServerResponse.createByErrorMessage("新建地址失败");
	}

	@Override
	public ServerResponse<String> del(Integer userId, Integer shippingId){
		int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
		if(resultCount > 0){
			return ServerResponse.createBySuccess("删除地址成功");
		}
		return ServerResponse.createByErrorMessage("删除地址失败");
	}

	@Override
	public ServerResponse<String> update(Integer userId, Shipping shipping){
		shipping.setUserId(userId);
		int rowCount = shippingMapper.updateByShipping(shipping);
		if(rowCount > 0){
			return ServerResponse.createBySuccess("更新地址成功");
		}
		return ServerResponse.createByErrorMessage("更新地址失败");
	}

	@Override
	public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
		Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
		if(shipping == null){
			return ServerResponse.createByErrorMessage("无法查询到该地址");
		}
		return ServerResponse.createBySuccess("更新地址成功",shipping);
	}
}