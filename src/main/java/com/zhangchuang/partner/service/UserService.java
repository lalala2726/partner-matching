package com.zhangchuang.partner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangchuang.partner.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * 脱敏敏感信息
     *
     * @param user 未脱敏的用户信息
     * @return 返回脱敏后的用户信息
     */
    User getSafeuser(User user);

    /**
     * 用户注销
     *
     * @param request 获取Session会话
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
     * 首页推荐用户，用户首次调用此方法会先在Redis查询是否含有数据如果有直接返回，如果没有查询数据然后存入Redis，然后返回数据
     *
     * @param pageSize 页面列数
     * @param pageNum  第几页
     * @param request  请求
     * @return 个性化推荐用户
     */
    Page<User> recommendUsers(Long pageSize, Long pageNum, HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @return 管理员返回true
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 查询当前登录的用户是否为管理员
     *
     */
    boolean isAdmin(User loginUser);
}
