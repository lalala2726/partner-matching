package com.zhangchuang.partner.service;

import com.zhangchuang.partner.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.zhangchuang.partner.contant.UserConstant.ADMIN_ROLE;
import static com.zhangchuang.partner.contant.UserConstant.USER_SESSION;

/**
 * @author chuang
 * 用户服务
 */
public interface UserService extends IService<User> {


    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验ID
     * @param planetId      星球编号
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetId);

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
     *
     * @param request
     */
    int logout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     */
    List<User> searchUsersByTags(List<String> tagNameList);


    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    int updateUser(User user, User loginUser);


    /**
     * 获取当前登录用户信息
     *
     * @return 返回当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @return 管理员返回true
     */
    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User loginUser);
}
