package com.zhangchuang.partner.service;

import com.zhangchuang.partner.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chuang
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-05-28 04:16:17
 * <p>
 * 用户服务
 */
public interface UserService extends IService<User> {


    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验ID
     * @param planetId 星球编号
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String planetId);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @return 返回脱敏后的用户信息
     */
    User getSafeuser(User user);

    /**
     * 用户注销
     * @param request
     */
    int logout(HttpServletRequest request);
}
