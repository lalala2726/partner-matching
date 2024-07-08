package com.zhangchuang.partner.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuang
 */
@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = 3248327974298L;


    /**
     * 队伍ID
     */
    @Schema(title = "队伍ID")
    private Long teamId;


    /**
     * 队伍密码
     */
    @Schema(title = "队伍密码")
    private String password;

}
