package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author julin
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加用户
     * @param user 用户
     * @return 状态
     */
    int insert(User user);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    /**
     * 通过id更新用户信息
     * @param user 用户
     * @return 状态
     */
    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User record);

    /**
     * 交友用户是否存在
     * @param userName 用户名
     * @return 状态
     */
    int checkUserName(String userName);

    /**
     * 检查邮箱是否已使用
     * @param email 邮箱
     * @return 状态
     */
    int checkEmail(String email);

    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @return 返回用户
     */
    User selectLogin(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户名关联问题
     * @param userName 用户名
     * @return 问题
     */
    String selectQusetionByUserName(String userName);

    /**
     * 查询问题设置的答案
     * @param userName 用户名
     * @param password 密码
     * @param answer 回答
     * @return 是否存在
     */
    int checkAnswer(@Param("userName")String userName,@Param("password")String password,@Param("answer")String answer);

    /**
     * 更新密码
     * @param userName 用户名
     * @param passwordNew 新密码
     * @return 状态
     */
    int updatePasswordByUserName(@Param("userName")String userName,@Param("passwordNew")String passwordNew);

    /**
     * 检查密码
     * @param password 密码
     * @param userId 用户id
     * @return 状态
     */
    int checkPassword(@Param("password")String password,@Param("userId")Integer userId);
}