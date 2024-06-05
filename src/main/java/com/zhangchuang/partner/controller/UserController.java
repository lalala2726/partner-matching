package com.zhangchuang.partner.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangchuang.partner.common.BaseResponse;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.common.ResultUtils;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.model.domain.request.UserLoginRequest;
import com.zhangchuang.partner.model.domain.request.UserRegisterRequest;
import com.zhangchuang.partner.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.zhangchuang.partner.contant.UserConstant.ADMIN_ROLE;
import static com.zhangchuang.partner.contant.UserConstant.USER_SESSION;

/**
 * Created Zhangchuang on 2024/5/28 16:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册需要的信息
     * @return 返回注册成功的ID
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetId = userRegisterRequest.getPlanetId();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetId)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetId);
        return ResultUtils.success(result);

    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 请求用户注销
     *
     * @param request
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int logout = userService.logout(request);
        return ResultUtils.success(logout);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_SESSION);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        User safeuser = userService.getSafeuser(userService.getById(userId));
        return ResultUtils.success(safeuser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {

        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_PERMISSIONS);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            userQueryWrapper.like("username", username);
        }
        List<User> list = userService.list(userQueryWrapper);
        List<User> collect = list.stream().map(userService::getSafeuser).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }


    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestParam("id") Long id, HttpServletRequest request) {
        //判断是否是管理员
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_PERMISSIONS);
        }
        //判断ID是否合法
        if (id >= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeById = userService.removeById(id);
        return ResultUtils.success(removeById);

    }


    /**
     * 是否为管理员
     *
     * @return 管理员返回true
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_SESSION);
        User user = (User) userObj;
        return user == null || user.getRole() != ADMIN_ROLE;
    }


}
