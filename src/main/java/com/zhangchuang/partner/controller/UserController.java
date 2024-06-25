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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.peer.CanvasPeer;
import java.util.List;
import java.util.stream.Collectors;

import static com.zhangchuang.partner.contant.UserConstant.ADMIN_ROLE;
import static com.zhangchuang.partner.contant.UserConstant.USER_SESSION;

/**
 * Created Zhangchuang on 2024/5/28 16:51
 */
@Tag(name = "用户", description = "用户管理")
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
    @Operation(summary = "注册",description = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister( @Parameter(description = "用户注册基本信息")
                                                @RequestBody UserRegisterRequest userRegisterRequest) {
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

    /**
     * 用户登录
     * @param userLoginRequest 登录参数
     * @param request       请求
     * @return 返回登录成功的用户信息
     */
    @PostMapping("/login")
    @Operation(summary = "登录",description ="用户登录")
    public BaseResponse<User> userLogin(@RequestBody @Parameter(description = "登录参数") UserLoginRequest userLoginRequest,
                                        @Parameter(description = "请求") HttpServletRequest request) {
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
     * @param request  请求
     */
    @Operation(summary = "注销",description ="用户注销")
    @PostMapping("/logout")
    public BaseResponse<Integer> logout(@Parameter(description = "请求") HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int logout = userService.logout(request);
        return ResultUtils.success(logout);
    }

    /**
     * 获取当前用户
     * @param request 请求
     * @return  返回当前用户
     */
    @Operation(summary = "获取当前用户",description ="获取当前用户")
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser( HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_SESSION);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        User safeuser = userService.getSafeuser(userService.getById(userId));
        return ResultUtils.success(safeuser);
    }

    /**
     * 搜索用户
     * @param username 用户名
     * @param request 请求
     * @return 返回用户列表
     */
    @Operation(summary = "搜索用户",description ="搜索用户")
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(@Parameter(description = "用户名") String username,
                                                @Parameter(description = "请求") HttpServletRequest request) {

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


    /**
     * 删除用户
     * @param id 用户ID
     * @param request 请求
     * @return  返回是否删除成功
     */
    @Operation(summary = "删除用户",description ="删除用户")
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestParam("id") @Parameter(description = "用户ID") Long id,
                                            @Parameter(description = "请求") HttpServletRequest request) {
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
     * 通过标签搜索用户
     * @param tagList 标签列表
     * @return  返回用户列表
     */
    @Operation(summary = "通过标签搜索用户",description ="通过标签搜索用户")
    @GetMapping
    public BaseResponse<List<User>> searchUserByTags(List<String> tagList){
        if (CollectionUtils.isEmpty(tagList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标签为空");
        }
        List<User> users = userService.searchUsersByTags(tagList);
        return ResultUtils.success(users);
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
