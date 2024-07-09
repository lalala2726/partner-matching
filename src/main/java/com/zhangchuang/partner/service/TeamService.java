package com.zhangchuang.partner.service;

import com.zhangchuang.partner.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.model.dto.TeamQuery;
import com.zhangchuang.partner.model.request.TeamJoinRequest;
import com.zhangchuang.partner.model.request.TeamQuitRequest;
import com.zhangchuang.partner.model.request.TeamUpdateRequest;
import com.zhangchuang.partner.model.vo.TeamUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chuang
 */
public interface TeamService extends IService<Team> {


    /**
     * 创建队伍
     *
     * @param team       队伍信息
     * @param longinUser 当前登录用户
     * @return 返回创建结果
     */
    long addTeam(Team team, User longinUser);


    /**
     * 搜索队伍
     *
     * @param teamQuery 参数
     * @return 返回队伍信息
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);


    /**
     * 更新队伍
     * @param teamUpdateRequest 可修改的队伍参数
     * @param loginUser 当前修改队伍的用户
     * @return 返回修改结果
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest 加入参数
     * @param loginUser 当前登录的用户
     * @return  返回加入结果
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);


    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);
}
