package com.zhangchuang.partner.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * <p>
 * Created Zhangchuang on 2024/5/28 16:55
 */
@Schema(title = "用户注册请求体")
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户账号")
    private String userAccount;
    @Schema(title = "用户密码")
    private String userPassword;
    @Schema(title = "校验密码")
    private String checkPassword;
    @Schema(title = "星球编号")
    private String planetId;

}
