package com.zhangchuang.partner.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuang
 */
@Schema(title = "用户信息")
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 用户编号
     */
    @Schema(title = "用户编号")
    @TableId(type = IdType.AUTO)
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
     * 密码
     */
    @Schema(title = "密码")
    private String userPassword;

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

    /**
     * 是否删除
     */
    @TableLogic
    @Schema(title = "是否删除")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
