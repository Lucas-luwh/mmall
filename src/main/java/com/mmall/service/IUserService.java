package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @create: 2020/11/15:15:44
 * @version: 1.0
 */
public interface IUserService {

	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return 返回体信息
	 */
	ServerResponse<User> login(String userName, String password);

	/**
	 * 用户注册
	 * @param user 用户
	 * @return 状态信息
	 */
	ServerResponse<String> register(User user);

	/**
	 * 校验用户名和邮箱是否存在
	 * @param str 用户名/邮箱
	 * @param type 判断类型
	 * @return 响应信息
	 */
	ServerResponse<String> checkValid(String str,String type);

	/**
	 * 查询设置的问题
	 * @param userName 用户名
	 * @return 返回问题
	 */
	ServerResponse<String> selectQuestion(String userName);

	/**
	 * 查询问题的答案
	 * @param userName 用户名
	 * @param question 问题
	 * @param answer 回答
	 * @return 信息
	 */
	ServerResponse<String> checkAnswer(String userName,String question,String answer);

	/**
	 * 重置密码
	 * @param userName 用户名
	 * @param passwordNew 新密码
	 * @param forgetToken 本地token
	 * @return 信息
	 */
	ServerResponse<String> forgetRestPassword(String userName,String passwordNew,String forgetToken);

	/**
	 * 重置用户密码
	 * @param passwordOld 旧密码
	 * @param passwordNew 新密码
	 * @param user 用户
	 * @return 响应体信息
	 */
	ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
}
