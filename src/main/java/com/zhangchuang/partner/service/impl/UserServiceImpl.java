package com.zhangchuang.partner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.mapper.UserMapper;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.zhangchuang.partner.contant.UserConstant.USER_SESSION;

/**
 * @author chuang
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-28 04:16:17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetId) {

        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号小于四位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于八位");
        }
        //账户不能包含特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9_]{4,16}$")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含特殊字符");
        }
        //密码和确认密码必须一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        //校验编号是否合法
        if (planetId.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号必须小于五位");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //注意！这边数据库的表名要和这边一致
        queryWrapper.eq("user_account", userAccount);
        //查询账户名是否已经被注册
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.ERROR, "此账户名已经注册");
        }
        //2.加密
        String md5Password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        //3.保存
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5Password);
        user.setPlanetId(planetId);
        //保存用户信息
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "服务器未能执行本次请求！请稍后再试或者联系管理员！");
        }
        //记住用户登录状态
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不合法");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能小于四位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能小于八位");
        }
        //账户不能包含特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9_]{4,16}$")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        //2.加密
        String md5Password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        //查询用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("user_password", md5Password);
        User user = this.getOne(userQueryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login filed userAccount:{}", userAccount);
            throw new BusinessException(ErrorCode.ERROR, "账号或密码错误");
        }
        //3.用户脱敏
        User safeUser = getSafeuser(user);
        //4.记录用户的登录状态
        request.getSession().setAttribute(USER_SESSION, user);
        log.info("user login successful,account:{}", userAccount);
        return safeUser;
    }


    @Override
    public User getSafeuser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetUser = new User();
        safetUser.setId(originUser.getId());
        safetUser.setUsername(originUser.getUsername());
        safetUser.setUserAccount(originUser.getUserAccount());
        safetUser.setAvatarUrl(originUser.getAvatarUrl());
        safetUser.setGender(originUser.getGender());
        safetUser.setPhone(originUser.getPhone());
        safetUser.setEmail(originUser.getEmail());
        safetUser.setUserStatus(originUser.getUserStatus());
        safetUser.setCreateTime(originUser.getCreateTime());
        safetUser.setRole(originUser.getRole());
        safetUser.setUserPassword(null);
        safetUser.setPlanetId(originUser.getPlanetId());
        return safetUser;
    }

    @Override
    public int logout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_SESSION);
        return 1;
    }
}




