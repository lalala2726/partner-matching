package com.zhangchuang.partner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.model.domain.Team;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.model.domain.UserTeam;
import com.zhangchuang.partner.model.enums.TeamStatusEnum;
import com.zhangchuang.partner.service.TeamService;
import com.zhangchuang.partner.mapper.TeamMapper;
import com.zhangchuang.partner.service.UserTeamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author chuang库操作Service实现
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
        implements TeamService {


    private final UserTeamService userTeamService;

    public TeamServiceImpl(UserTeamService userTeamService) {
        this.userTeamService = userTeamService;
    }

    /**
     * 创建队伍并校验队伍信息
     *
     * @param team       队伍信息
     * @param longinUser 当前登录用户
     * @return 返回创建结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long addTeam(Team team, User longinUser) {
        //1.请求参数是否为空
        if (team == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法!");
        }
        //2.是否登录，未登录不允许创建
        if (longinUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "请先登录！");
        }
        //查询当前用户的ID
        final Long userId = longinUser.getId();
        //检验信息
        //1.队伍人数 > 1 且 <= 20
        int maxNumber = Optional.ofNullable(team.getMaxNumber()).orElse(0);
        if (maxNumber < 1 || maxNumber > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍人数不满足要求");
        }
        //2队伍标题 <= 20
        String name = team.getName();
        if (StringUtils.isBlank(name) || name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍标题不满足要求");
        }
        //3.队伍描述 <= 512
        String description = team.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍描述过长");
        }
        //4.status 是否公开(int) 不传默认为0(公开)
        int status = Optional.ofNullable(team.getStatus()).orElse(0);
        TeamStatusEnum statusEnum = TeamStatusEnum.getEnumByValue(status);
        if (statusEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍状态不满足要求");
        }
        //5.如果 status 是加密状态，一定要有密码，且密码 <= 32
        String password = team.getPassword();
        if (TeamStatusEnum.SECURE.equals(statusEnum) && (StringUtils.isBlank(password) || password.length() > 32)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码设置不正确");
        }
        //6.超时时间 > 当前时间
        Date createTime = team.getExpireTime();
        if (new Date().after(createTime)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "超时时间大于当前时间");
        }
        //7.检验用户最多创建5个队伍
        //todo 有bug，可能会同时创建100个队伍，2024年7月3日19:16:45
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("user_id", userId);
        long count = this.count(teamQueryWrapper);
        if (count > 5) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "您最多只能创建5个队伍");
        }
        //8.插入队伍信息到队伍表
        team.setId(null);
        team.setUserId(userId);
        boolean result = this.save(team);
        Long teamId = team.getId();
        if (!result || teamId == null) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "创建队伍失败！");
        }
        //9.插入用户 => 队伍关系到关系表
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(userId);
        userTeam.setTeamId(teamId);
        userTeam.setJoinTime(new Date());
        boolean resultUserTeam = userTeamService.save(userTeam);
        if (!resultUserTeam) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "创建队伍失败！");
        }
        return teamId;
    }
}




