package com.zhangchuang.partner.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍请求体
 *
 * @author chuang
 */
@Data
public class TeamQuitRequest implements Serializable {


    private static final long serialVersionUID = -7210458883136586754L;


    /**
     * 队伍ID
     */
    @Schema(title = "队伍ID")
    private Long teamId;

}
