package com.zhangchuang.partner.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 队伍表
 *
 */
@TableName(value = "team")
@Data
public class Team implements Serializable {
    /**
     *
     */
    @Schema(title = "队伍编号")
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(title = "用修改时间")
    private Date updateTime;

    /**
     * 是否删除
     */
    @Schema(title = "是否删除")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
