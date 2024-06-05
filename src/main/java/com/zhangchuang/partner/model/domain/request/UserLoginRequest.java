package com.zhangchuang.partner.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * <p>
 * Created Zhangchuang on 2024/5/28 16:55
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2L;
    private String userAccount;
    private String userPassword;

}
