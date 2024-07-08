package com.zhangchuang.partner.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 队伍和用户信息封装类(脱敏)
 *
 * @author chuang
 */
@Data
public class TeamUserVO implements Serializable {

    private static final long serialVersionUID = 260995623971166098L;
    /**
     * 队伍编号
     */
    @Schema(title = "队伍编号")
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
     * 创建时间
     */
    @Schema(title = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(title = "修改时间")
    private Date updateTime;


    /**
     * 创建人入队列表
     */
    @Schema(title = "创建人入队列表 ")
    UserVO createUser;



}
