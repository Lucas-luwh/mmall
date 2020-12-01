package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ErrorConst;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
	@Autowired
	private IFileService iFileService;

	@RequestMapping("save.do")
	@ResponseBody
	public ServerResponse<String> productSave(HttpSession session, Product product){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.saveOrUpdateProduct(product);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("set_sale_status.do")
	@ResponseBody
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId,Integer status){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.setSaleStatus(productId,status);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("detail.do")
	@ResponseBody
	public ServerResponse<ProductDetailVo> getDetail(HttpSession session, Integer productId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.manageProductDetail(productId);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("list.do")
	@ResponseBody
	public ServerResponse<PageInfo<ProductListVo>> getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.getProductList(pageNum,pageSize);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("search.do")
	@ResponseBody
	public ServerResponse<PageInfo<ProductListVo>> productSearch(HttpSession session,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.searchProduct(productName,productId,pageNum,pageSize);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("upload.do")
	@ResponseBody
	public ServerResponse<Map<String,String>> upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ErrorConst.NEED_LOGIN_MESSAGE);
		}
		if (iUserService.checkAdminRole(user).isSuccess()){
			String path = request.getSession().getServletContext().getRealPath("upload");
			String targetFileName = iFileService.upload(file, path);
			String url = String.format("%s%s", PropertiesUtil.getProperty("ftp.server.http.prefix"), targetFileName);

			Map<String,String> fileMap = Maps.newHashMap();
			fileMap.put("uri",targetFileName);
			fileMap.put("url",url);
			return ServerResponse.createBySuccess(fileMap);
		}
		return ServerResponse.createByErrorMessage(ErrorConst.NEED_AUTHORITY);
	}

	@RequestMapping("richtext_img_upload.do")
	@ResponseBody
	public Map<String,Object> richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap = Maps.newHashMap();
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		if(user == null){
			resultMap.put(Const.SUCCESS,false);
			resultMap.put("msg","请登录管理员");
			return resultMap;
		}

		if (iUserService.checkAdminRole(user).isSuccess()){
			String path = request.getSession().getServletContext().getRealPath("upload");
			String targetFileName = iFileService.upload(file, path);
			if (StringUtils.isBlank(targetFileName)){
				resultMap.put(Const.SUCCESS,false);
				resultMap.put("msg","上传失败");
				return resultMap;
			}
			String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
			resultMap.put(Const.SUCCESS,true);
			resultMap.put("msg","上传成功");
			resultMap.put("file_path",url);
			response.addHeader("Access-Control-Allow-Headers","X-File-Name");
		}else {
			resultMap.put(Const.SUCCESS,false);
			resultMap.put("msg",ErrorConst.NEED_AUTHORITY);
		}
		return resultMap;
	}
}
