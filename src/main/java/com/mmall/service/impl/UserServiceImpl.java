package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.RoleImpl;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description: 用户实现类
 * @create: 2020/11/15:15:48
 * @version: 1.0
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return 响应返回体信息
	 */
	@Override
	public ServerResponse<User> login(String userName, String password) {
		int resultCount = userMapper.checkUserName(userName);
		if (resultCount == 0){
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		//md5密码登录
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(userName, md5Password);
		if (user == null){
			return ServerResponse.createByErrorMessage("密码错误");
		}
		user.setPassword(StringUtils.EMPTY);
		return ServerResponse.createBySuccess("登录成功",user);
	}

	/**
	 * 用户注册
	 * @param user 用户
	 * @return 状态信息
	 */
	@Override
	public ServerResponse<String> register(User user){
		ServerResponse<String> valid = this.checkValid(user.getUsername(), Const.USERNAME);
		if (!valid.isSuccess()){
			return valid;
		}
		valid = this.checkValid(user.getEmail(),Const.EMAIL);
		if (!valid.isSuccess()){
			return valid;
		}
		//设置权限为普通用户
		user.setRole(RoleImpl.ROLE_CUSTOMER);
		//md5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		int resultCount = userMapper.insert(user);
		if (resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	@Override
	public ServerResponse<String> checkValid(String str, String type){
		if (StringUtils.isNotBlank(type)){
			if (Const.USERNAME.equals(type)){
				int resultCount = userMapper.checkUserName(str);
				if (resultCount > 0){
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}
			if (Const.EMAIL.equals(type)){
				int resultCount = userMapper.checkEmail(str);
				if (resultCount > 0){
					return ServerResponse.createByErrorMessage("email已存在");
				}
			}
		}else {
			return ServerResponse.createByErrorMessage("参数错误");
		}
		return ServerResponse.createBySuccessMessage("校验成功");
	}

	@Override
	public ServerResponse<String> selectQuestion(String userName){
		ServerResponse<String> valid = this.checkValid(userName, Const.USERNAME);
		if (valid.isSuccess()){
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String qusetion = userMapper.selectQusetionByUserName(userName);
		if (StringUtils.isNotBlank(qusetion)){
			return ServerResponse.createBySuccess(qusetion);
		}
		return ServerResponse.createByErrorMessage("找回密码的问题是空的");
	}

	@Override
	public ServerResponse<String> checkAnswer(String userName, String question, String answer){
		int resultCount = userMapper.checkAnswer(userName, question, answer);
		if (resultCount > 0){
			//问题答案正确
			String forgetToken = UUID.randomUUID().toString();
			TokenCache.setKey(TokenCache.TOKEN_PREFIX+userName,forgetToken);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMessage("问题的答案错误");
	}

	@Override
	public ServerResponse<String> forgetRestPassword(String userName, String passwordNew, String forgetToken){
		if (StringUtils.isBlank(forgetToken)){
			return ServerResponse.createByErrorMessage("参数错误，token需传递");
		}

		ServerResponse<String> valid = this.checkValid(userName, Const.USERNAME);
		if (valid.isSuccess()){
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + userName);
		if (StringUtils.isBlank(token)){
			return ServerResponse.createByErrorMessage("token无效或者已过期");
		}
		if (StringUtils.equals(forgetToken,token)){
			String password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUserName(userName, password);
			if (rowCount > 0){
				return ServerResponse.createBySuccessMessage("修改密码成功");
			}
		}else {
			return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
		}
		return ServerResponse.createByErrorMessage("修改密码失败");
	}

	@Override
	public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
		//防止横向越权
		int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
		if (resultCount == 0){
			return ServerResponse.createByErrorMessage("旧密码错误");
		}
		user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
		int updateCount = userMapper.updateByPrimaryKeySelective(user);
		if (updateCount > 0){
			return ServerResponse.createBySuccessMessage("密码更新成功");
		}
		return ServerResponse.createByErrorMessage("密码更新失败");
	}

	@Override
	public ServerResponse<User> updateInformation(User user){
		//username是不能被更新的
		//email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
		int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
		if(resultCount > 0){
			return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
		}
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setEmail(user.getEmail());
		updateUser.setPhone(user.getPhone());
		updateUser.setQuestion(user.getQuestion());
		updateUser.setAnswer(user.getAnswer());

		int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
		if(updateCount > 0){
			return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
		}
		return ServerResponse.createByErrorMessage("更新个人信息失败");
	}

	@Override
	public ServerResponse<User> getInformation(Integer userId){
		User user = userMapper.selectByPrimaryKey(userId);
		if(user == null){
			return ServerResponse.createByErrorMessage("找不到当前用户");
		}
		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
		return ServerResponse.createBySuccess(user);

	}

	/**
	 * 校验是否是管理员
	 * @param user 用户
	 * @return 响应信息
	 */
	@Override
	public ServerResponse<String> checkAdminRole(User user){
		if(user != null && user.getRole() == RoleImpl.ROLE_ADMIN){
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}
}
