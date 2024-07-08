package com.zhangchuang.partner.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户包装类（脱敏）
 *
 * @author chuang
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -3063730116564880030L;


    /**
     * 用户编号
     */
    @Schema(title = "用户编号")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(title = "用户昵称")
    private String username;

    /**
     * 用户账号
     */
    @Schema(title = "用户账号")
    private String userAccount;

    /**
     * 标签
     */
    @Schema(title = "标签")
    private String tags;

    /**
     * 用户头像
     */
    @Schema(title = "用户头像")
    private String avatarUrl;

    /**
     * 性别
     */
    @Schema(title = "性别")
    private Integer gender;

    /**
     * 默认用户 0 ,管理员 1
     */
    @Schema(title = "默认用户 0 ,管理员 1")
    private Integer role;

    /**
     * 手机号码
     */
    @Schema(title = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(title = "邮箱")
    private String email;

    /**
     * 状态 0正常
     */
    @Schema(title = "状态 0正常")
    private Integer userStatus;

    /**
     * 星球编号
     */
    @Schema(title = "星球编号")
    private String planetId;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(title = "更新时间")
    private Date updateTime;


}
