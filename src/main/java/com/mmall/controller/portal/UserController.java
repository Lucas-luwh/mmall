package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @package: com.mmall.controller.portal
 * @author: luweihong
 * @create: 2020/11/15:15:32
 * @version: 1.0
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private IUserService iUserService;

	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 * @param session 缓存
	 * @return 服务端响应信息
	 */
	@RequestMapping(value = "login.do",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login(String userName, String password, HttpSession session){
		ServerResponse<User> response = iUserService.login(userName, password);
		if (response.isSuccess()){
			session.setAttribute(Const.CURRENT_USER,response.getData());
		}
		return response;
	}

	/**
	 * 登出
	 * @param session 缓存
	 * @return 返回状态
	 */
	@RequestMapping(value = "loginout.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> loginOut(HttpSession session){
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}

	@RequestMapping(value = "register.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> register(User user){
		return iUserService.register(user);
	}

	@RequestMapping(value = "check_valid.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> checkValid(String str, String type){
		return iUserService.checkValid(str,type);
	}

	/**
	 * 获取用户信息
	 * @param session 缓存
	 * @return 状态信息
	 */
	@RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<User> getUserInfo(HttpSession session){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		Assert.notNull(user,ServerResponse.createBySuccess(user).getMsg());
		return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
	}

	@RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> forgetGetQuestion(String userName){
		return iUserService.selectQuestion(userName);
	}

	@RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> forgetCheckAnswer(String userName,String question,String answer){
		return iUserService.checkAnswer(userName,question,answer);
	}

	@RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> forgetRestPassword(String userName,String passwordNew,String forgetToken){
		return iUserService.forgetRestPassword(userName,passwordNew,forgetToken);
	}

	@RequestMapping(value = "reset_password.do",method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null){
			return ServerResponse.createByErrorMessage("用户未登录");
		}
		return iUserService.resetPassword(passwordOld,passwordNew,user);

	}
}
