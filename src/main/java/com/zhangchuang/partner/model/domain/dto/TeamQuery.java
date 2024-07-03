package com.zhangchuang.partner.model.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chuang
 */
public class TeamQuery {
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
     * 用户ID
     */
    @Schema(title = "用户ID")
    private Long userId;

    /**
     * 1 - 公开 ,1 - 私有 ,2 - 加密
     */
    @Schema(title = "队伍状态")
    private Integer status;

}
