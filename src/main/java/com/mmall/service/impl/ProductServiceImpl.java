package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.Dict;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description:
 * @create: 2020/11/23:22:08
 * @version: 1.0
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ICategoryService iCategoryService;

	@Override
	public ServerResponse<String>  saveOrUpdateProduct(Product product){
		if (product != null){
			if (StringUtils.isNotBlank(product.getSubImages())){
				String[] subImageArray = product.getSubImages().split(",");
				if (subImageArray.length > 0){
					product.setMainImage(subImageArray[0]);
				}
			}

			if (product.getId() != null){
				int count = productMapper.updateByPrimaryKey(product);
				if (count > 0){
					return ServerResponse.createBySuccessMessage("更新产品成功");
				}
				return ServerResponse.createByErrorMessage("更新产品失败");
			}else{
				int count = productMapper.insert(product);
				if (count > 0){
					return ServerResponse.createBySuccessMessage("新增产品成功");
				}
				return ServerResponse.createBySuccessMessage("新增产品失败");
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
	}

	@Override
	public ServerResponse<String> setSaleStatus(Integer productId, Integer status){
		if (productId == null || status == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}

		Product product = new Product();
		product.setId(productId);
		product.setStatus(status);

		int count = productMapper.updateByPrimaryKeySelective(product);
		if(count > 0){
			return ServerResponse.createBySuccess("修改产品销售状态成功");
		}
		return ServerResponse.createByErrorMessage("修改产品销售状态失败");
	}

	@Override
	public ServerResponse<ProductDetailVo>  manageProductDetail(Integer productId){
		if (productId == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}

		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null){
			return ServerResponse.createByErrorMessage(Dict.PRODUCT_OFFSHELF_DELETE);
		}
		ProductDetailVo productDetailVo = assembleProductDetailVo(product);
		return ServerResponse.createBySuccess(productDetailVo);
	}

	private ProductDetailVo assembleProductDetailVo(Product product){
		ProductDetailVo productDetailVo = new ProductDetailVo();
		productDetailVo.setId(product.getId());
		productDetailVo.setSubtitle(product.getSubtitle());
		productDetailVo.setPrice(product.getPrice());
		productDetailVo.setMainImage(product.getMainImage());
		productDetailVo.setSubImages(product.getSubImages());
		productDetailVo.setCategoryId(product.getCategoryId());
		productDetailVo.setDetail(product.getDetail());
		productDetailVo.setName(product.getName());
		productDetailVo.setStatus(product.getStatus());
		productDetailVo.setStock(product.getStock());

		productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
		Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
		if (category == null){
			//根节点
			productDetailVo.setParentCategoryId(0);
		}else{
			productDetailVo.setParentCategoryId(category.getParentId());
		}

		productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
		productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
		return productDetailVo;
	}

	@Override
	public ServerResponse<PageInfo<ProductListVo>> getProductList(int pageNum, int pageSize){
		//分页插件
		PageHelper.startPage(pageNum,pageSize);

		List<Product> productList = productMapper.selectList();
		List<ProductListVo> productListVos = productList.stream().map(this::assembleProductListVo).collect(Collectors.toList());
		PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVos);
		pageInfo.setList(productListVos);
		return ServerResponse.createBySuccess(pageInfo);
	}

	private ProductListVo assembleProductListVo(Product product){
		ProductListVo productListVo = new ProductListVo();
		productListVo.setId(product.getId());
		productListVo.setName(product.getName());
		productListVo.setCategoryId(product.getCategoryId());
		productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
		productListVo.setMainImage(product.getMainImage());
		productListVo.setPrice(product.getPrice());
		productListVo.setSubtitle(product.getSubtitle());
		productListVo.setStatus(product.getStatus());
		return productListVo;
	}

	@Override
	public ServerResponse<PageInfo<ProductListVo>> searchProduct(String productName, Integer productId, int pageNum, int pageSize){
		PageHelper.startPage(pageNum,pageSize);
		if (StringUtils.isNotBlank(productName)){
			productName = "%" + productName + "%";
		}
		List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
		ArrayList<ProductListVo> list = Lists.newArrayList();
		for (Product productItem:productList) {
			ProductListVo productListVo = assembleProductListVo(productItem);
			list.add(productListVo);
		}
		PageInfo<ProductListVo> pageInfo = new PageInfo<>(list);
		pageInfo.setList(list);
		return ServerResponse.createBySuccess(pageInfo);
	}

	@Override
	public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
		if(productId == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null){
			return ServerResponse.createByErrorMessage(Dict.PRODUCT_OFFSHELF_DELETE);
		}
		if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
			return ServerResponse.createByErrorMessage(Dict.PRODUCT_OFFSHELF_DELETE);
		}
		ProductDetailVo productDetailVo = assembleProductDetailVo(product);
		return ServerResponse.createBySuccess(productDetailVo);
	}

	@Override
	public ServerResponse<PageInfo<ProductListVo>> getProductByKeywordCategory(String keyWord, Integer categoryId, int pageNum, int pageSize, String orderBy){
		if (StringUtils.isBlank(keyWord) && categoryId == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		List<Integer> categoryIdList = Lists.newArrayList();
		if (categoryId != null){
			Category category = categoryMapper.selectByPrimaryKey(categoryId);
			if (category == null && StringUtils.isBlank(keyWord)){
				PageHelper.startPage(pageNum,pageSize);
				List<ProductListVo> productListVoList = Lists.newArrayList();
				PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVoList);
				return ServerResponse.createBySuccess(pageInfo);
			}
			assert category != null;
			categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
		}
		if (StringUtils.isNotBlank(keyWord)){
			keyWord = "%"+keyWord+"%";
		}
		PageHelper.startPage(pageNum,pageSize);
		if (StringUtils.isNotBlank(orderBy) && Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
			String[] array = orderBy.split(",");
			PageHelper.orderBy(array[0]+" "+array[1]);
		}

		List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyWord) ? null : keyWord, categoryIdList.isEmpty() ? null : categoryIdList);
		List<ProductListVo> productListVoList = productList.stream().map(this::assembleProductListVo).collect(Collectors.toList());

		PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVoList);
		pageInfo.setList(productListVoList);
		return ServerResponse.createBySuccess(pageInfo);
	}
}
