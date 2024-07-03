package com.zhangchuang.partner.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuang
 */
@Data
public class TeamAddRequest implements Serializable {

    private static final long serialVersionUID = 3248327974298L;

    /**
     * 队伍名称
     */
    @Schema(title = "队伍名称")
    private String name;

    /**
     * 队伍描述
     */
    @Schema(title = "队伍描述")
    private String description;

    /**
     * 队伍最大人数
     */
    @Schema(title = "队伍最大人数")
    private Integer maxNumber;

    /**
     * 过期时间
     */
    @Schema(title = "队伍过期时间")
    private Date expireTime;

    /**
     * 用户ID
     */
    @Schema(title = "用户ID")
    private Long userId;

    /**
     * 1 - 公开 ,1 - 私有 ,2 - 加密
     */
    @Schema(title = "队伍状态")
    private Integer status;

    /**
     * 密码
     */
    @Schema(title = "队伍密码")
    private String password;

}
