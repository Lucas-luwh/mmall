package com.mmall.service.impl;

import com.google.common.annotations.Beta;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.CartImpl;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description:
 * @create: 2020/12/2:21:50
 * @version: 1.0
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private ProductMapper productMapper;

	@Override
	public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count){
		if(productId == null || count == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}

		Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
		if (cart == null){
			//产品不在购物车里,新增一个产品
			Cart cartItem = new Cart();
			cartItem.setQuantity(count);
			cartItem.setChecked(CartImpl.CHECKED);
			cartItem.setProductId(productId);
			cartItem.setUserId(userId);
			cartMapper.insert(cartItem);
		}else {
			//产品已存在，数量相加
			count = cart.getQuantity() + count;
			cart.setQuantity(count);
			cartMapper.updateByPrimaryKeySelective(cart);
		}
		return this.list(userId);
	}

	public ServerResponse<CartVo> list(Integer userId){
		CartVo cartVo = this.getCartVoLimit(userId);
		return ServerResponse.createBySuccess(cartVo);
	}

	private CartVo getCartVoLimit(Integer userId){
		CartVo cartVo = new CartVo();
		List<Cart> cartList = cartMapper.selectCartByUserId(userId);
		List<CartProductVo> cartProductVoList = Lists.newArrayList();

		BigDecimal cartTotalPrice = new BigDecimal("0");

		if (CollectionUtils.isNotEmpty(cartList)){
			for (Cart cartItem : cartList){
				CartProductVo cartProductVo = new CartProductVo();
				cartProductVo.setId(cartItem.getId());
				cartProductVo.setUserId(userId);
				cartProductVo.setProductId(cartItem.getProductId());

				Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
				if (product != null){
					cartProductVo.setProductMainImage(product.getMainImage());
					cartProductVo.setProductName(product.getName());
					cartProductVo.setProductSubtitle(product.getSubtitle());
					cartProductVo.setProductStatus(product.getStatus());
					cartProductVo.setProductPrice(product.getPrice());
					cartProductVo.setProductStock(product.getStock());

					//判断库存
					int buyLimitCount = 0;
					if (product.getStock() >= cartItem.getQuantity()){
						//库存充足
						buyLimitCount = cartItem.getQuantity();
						cartProductVo.setLimitQuantity(CartImpl.LIMIT_NUM_SUCCESS);
					}else{
						buyLimitCount = product.getStock();
						cartProductVo.setLimitQuantity(CartImpl.LIMIT_NUM_FAIL);
						//更新库存
						Cart cart = new Cart();
						cart.setId(cartItem.getId());
						cart.setQuantity(buyLimitCount);
						cartMapper.updateByPrimaryKeySelective(cart);
					}
					cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
					cartProductVo.setProductChecked(cartItem.getChecked());
				}

				if(cartItem.getChecked() == CartImpl.CHECKED){
					//如果已经勾选,增加到整个的购物车总价中
					cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
				}
				cartProductVoList.add(cartProductVo);
			}
		}

		cartVo.setCartTotalPrice(cartTotalPrice);
		cartVo.setCartProductVoList(cartProductVoList);
		cartVo.setAllChecked(this.getAllCheckedStatus(userId));
		cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
		return cartVo;
	}

	private boolean getAllCheckedStatus(Integer userId){
		if(userId == null){
			return false;
		}
		return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
	}

	@Override
	public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count){
		if(productId == null || count == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
		if (cart != null){
			cart.setQuantity(count);
		}
		cartMapper.updateByPrimaryKey(cart);
		return this.list(userId);
	}

	@Override
	@Beta
	public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
		List<String> productList = Splitter.on(",").splitToList(productIds);
		if(CollectionUtils.isEmpty(productList)){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		cartMapper.deleteByUserIdProductIds(userId,productList);
		return this.list(userId);
	}
}
