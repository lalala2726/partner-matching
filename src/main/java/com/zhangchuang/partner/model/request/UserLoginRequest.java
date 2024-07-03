package com.zhangchuang.partner.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author chuang
 */
@Schema(title = "用户注册请求体")
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2L;
    @Schema(title = "用户账号")
    private String userAccount;
    @Schema(title = "用户密码")
    private String userPassword;

}
