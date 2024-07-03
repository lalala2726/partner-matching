package com.zhangchuang.partner.controller;

import com.zhangchuang.partner.common.BaseResponse;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.common.ResultUtils;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.model.domain.Team;
import com.zhangchuang.partner.model.domain.dto.TeamQuery;
import com.zhangchuang.partner.service.TeamService;
import com.zhangchuang.partner.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chuang
 */
@Tag(name = "队伍管理", description = "队伍相关的操作")
@Slf4j
@RequestMapping("/team")
@RestController
public class TeamController {

    private final UserService userService;

    private final TeamService teamService;

    public TeamController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }


    @PostMapping
    @Operation(summary = "添加队伍")
    public BaseResponse<Long> addTeam(@Parameter(description = "队伍信息") @RequestBody Team team) {
        if (team == null) {
            throw new BusinessException(ErrorCode.ERROR, "参数非法！");
        }
        boolean save = teamService.save(team);
        if (!save) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "添加失败！");
        }
        return ResultUtils.success(team.getId());
    }


    @PostMapping
    @Operation(summary = "删除队伍")
    public BaseResponse<Boolean> delTeam(@Parameter(description = "被删除队伍的编号") Long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法！");
        }
        boolean result = teamService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "删除失败！");
        }
        return ResultUtils.success(true);
    }


    @PostMapping
    @Operation(summary = "更新队伍")
    public BaseResponse<Boolean> updateTeam(@Parameter(description = "传入修改后的队伍信息，注意ID不能被修改") @RequestBody Team team) {
        if (team == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法！");
        }
        boolean result = teamService.updateById(team);
        if (!result) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "更新失败！");
        }
        return ResultUtils.success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "根据编号获取指定的队伍信息")
    public BaseResponse<Team> getTeamById(@Parameter(description = "被查询的队伍编号") long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法");
        }
        Team result = teamService.getById(id);
        if (result == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "未找到对应的数据");
        }
        return ResultUtils.success(result);
    }


    @GetMapping("/list")
    public BaseResponse<List<Team>> listTeams(@RequestBody TeamQuery teamQuery) {
        return null;
    }

}
