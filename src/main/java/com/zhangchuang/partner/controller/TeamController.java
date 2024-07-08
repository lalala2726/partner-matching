package com.zhangchuang.partner.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangchuang.partner.common.BaseResponse;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.common.ResultUtils;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.model.domain.Team;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.model.dto.TeamQuery;
import com.zhangchuang.partner.model.request.TeamAddRequest;
import com.zhangchuang.partner.model.request.TeamJoinRequest;
import com.zhangchuang.partner.model.request.TeamUpdateRequest;
import com.zhangchuang.partner.model.vo.TeamUserVO;
import com.zhangchuang.partner.service.TeamService;
import com.zhangchuang.partner.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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


    /**
     * 添加队伍
     *
     * @param teamAddRequest 队伍参数
     * @return 返回添加结果
     */
    @PostMapping("/add")
    @Operation(summary = "添加队伍")
    public BaseResponse<Long> addTeam(@Parameter(description = "队伍信息") @RequestBody TeamAddRequest teamAddRequest,
                                      HttpServletRequest request) {
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.ERROR, "参数非法！");
        }
        User loginUser = userService.getLoginUser(request);
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest, team);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }


    /**
     * 删除队伍
     *
     * @param id 队伍编号
     * @return 返回删除结果
     */
    @PostMapping("/delete")
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


    /**
     * 更新队伍信息
     *
     * @param teamUpdateRequest 队伍信息
     * @return 返回更新结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新队伍")
    public BaseResponse<Boolean> updateTeam(@Parameter(description = "传入修改后的队伍信息，注意ID不能被修改")
                                            @RequestBody TeamUpdateRequest teamUpdateRequest,
                                            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法！");
        }
        boolean result = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "更新失败！");
        }
        return ResultUtils.success(true);
    }


    /**
     * 根据编号获取队伍信息
     *
     * @param id 队伍编号
     * @return 返回队伍信息
     */
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


    /**
     * 获取队伍列表
     *
     * @param teamQuery 查询条件
     * @return 返回查询的结果
     */
    @Operation(summary = "获取队伍列表")
    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> listTeams(@RequestBody TeamQuery teamQuery, HttpServletRequest request) {
        System.out.println("请求参数：" + teamQuery);
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法!");
        }
        boolean isAdmin = userService.isAdmin(request);
        List<TeamUserVO> list = teamService.listTeams(teamQuery, isAdmin);
        return ResultUtils.success(list);
    }

    /**
     * 分页获取队伍列表
     *
     * @param teamQuery 查询条件
     * @return 返回查询的结果
     */
    @Operation(summary = "分页获取队伍列表")
    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamsByPage(TeamQuery teamQuery) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法!");
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamQuery, team);
        Page<Team> page = new Page<>(teamQuery.getPageNum(), teamQuery.getPageSite());
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Page<Team> resultPage = teamService.page(page, queryWrapper);
        return ResultUtils.success(resultPage);
    }


    /**
     * 加入队伍
     * @param teamJoinRequest 请求参数
     * @return  加入结果
     */
    @GetMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest,HttpServletRequest request){
        if (teamJoinRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean team = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.success(team);
    }

}
