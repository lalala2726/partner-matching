package com.zhangchuang.partner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.exception.BusinessException;
import com.zhangchuang.partner.mapper.TeamMapper;
import com.zhangchuang.partner.model.domain.Team;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.model.domain.UserTeam;
import com.zhangchuang.partner.model.dto.TeamQuery;
import com.zhangchuang.partner.model.enums.TeamStatusEnum;
import com.zhangchuang.partner.model.request.TeamJoinRequest;
import com.zhangchuang.partner.model.request.TeamQuitRequest;
import com.zhangchuang.partner.model.request.TeamUpdateRequest;
import com.zhangchuang.partner.model.vo.TeamUserVO;
import com.zhangchuang.partner.model.vo.UserVO;
import com.zhangchuang.partner.service.TeamService;
import com.zhangchuang.partner.service.UserService;
import com.zhangchuang.partner.service.UserTeamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author chuang库操作Service实现
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
        implements TeamService {


    private final UserTeamService userTeamService;

    private final UserService userService;

    public TeamServiceImpl(UserTeamService userTeamService, UserService userService) {
        this.userTeamService = userTeamService;
        this.userService = userService;
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

    @Override
    public List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin) {

        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();

        //组合查询条件
        if (teamQuery != null) {
            Long id = teamQuery.getId();
            //根据ID查询
            if (id != null && id > 0) {
                teamQueryWrapper.eq("id", id);
            }
            //根据队伍名称查询
            String name = teamQuery.getName();
            if (StringUtils.isNotBlank(name)) {
                teamQueryWrapper.like("name", name);
            }
            String keyword = teamQuery.getKeyword();
            if (StringUtils.isNotBlank(keyword)) {
                teamQueryWrapper.and(query -> query.like("name", keyword).or()
                        .like("description", keyword));
            }
            //根据队伍描述查询
            String description = teamQuery.getDescription();
            if (StringUtils.isNotBlank(description)) {
                teamQueryWrapper.like("description", description);
            }
            //根据最大人数查询
            Integer maxNumber = teamQuery.getMaxNumber();
            if (maxNumber != null && maxNumber > 0) {
                // 查询最大人数相等的
                teamQueryWrapper.lt("max_number", maxNumber);
            }
            //根据队长查询
            Long userId = teamQuery.getUserId();
            //根据创建人查询
            if (userId != null && userId > 0) {
                teamQueryWrapper.eq("user_id", userId);
            }
            //根据状态来查询
            Integer status = teamQuery.getStatus();
            TeamStatusEnum statusEnum = TeamStatusEnum.getEnumByValue(status);
            if (statusEnum == null) {
                statusEnum = TeamStatusEnum.PUBLIC;
            }
            if (!isAdmin && !statusEnum.equals(TeamStatusEnum.PUBLIC)) {
                throw new BusinessException(ErrorCode.NO_PERMISSIONS, "您没有权限查看该队伍");
            }
            teamQueryWrapper.eq("status", statusEnum.getValue());
        }
        //不展示已过期的队伍
        teamQueryWrapper.and(qw -> qw.gt("expire_time", new Date()))
                .or()
                .isNull("expire_time");

        List<Team> teamList = this.list(teamQueryWrapper);
        if (CollectionUtils.isEmpty(teamList)) {
            return new ArrayList<>();
        }
        List<TeamUserVO> teamUserVOSList = new ArrayList<>();
        //关联查询创建人的信息
        for (Team team : teamList) {
            Long userId = team.getUserId();
            if (userId == null) {
                continue;
            }
            User user = userService.getById(userId);
            TeamUserVO teamUserVO = new TeamUserVO();
            BeanUtils.copyProperties(team, teamUserVO);
            //脱敏用户信息
            if (user != null) {
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(user, userVO);
                teamUserVO.setCreateUser(userVO);
            }
            teamUserVOSList.add(teamUserVO);
        }
        return teamUserVOSList;
    }


    @Override
    public boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser) {
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法！");
        }
        Long id = teamUpdateRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法！");
        }
        Team oldTeam = this.getById(id);
        if (oldTeam == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "查无此队伍！");
        }
        //只有管理员或者队伍的创建者可以修改
        if (oldTeam.getUserId() != loginUser.getId() && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_PERMISSIONS, "您没有此权限！");
        }
        //获取当前要要更改的状态
        TeamStatusEnum statusEnum = TeamStatusEnum.getEnumByValue(teamUpdateRequest.getStatus());
        if (statusEnum.equals(TeamStatusEnum.SECURE)) {
            if (StringUtils.isNotBlank(teamUpdateRequest.getPassword())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "加密房间必须设置密码!");
            }
        }
        Team updateTeam = new Team();
        BeanUtils.copyProperties(teamUpdateRequest, updateTeam);
        return this.updateById(updateTeam);
    }

    @Override
    public boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser) {
        //FIXME 并发请求，用户可能多次加入此队伍
        if (teamJoinRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long teamId = teamJoinRequest.getTeamId();
        if (teamId == null || teamId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = this.getById(teamId);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍不存在!");
        }
        if (team.getExpireTime() != null && team.getExpireTime().before(new Date())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "当前队伍已过期");
        }
        Integer status = team.getStatus();
        TeamStatusEnum teamStatusEnum = TeamStatusEnum.getEnumByValue(status);
        if (TeamStatusEnum.PRIVATE.equals(teamStatusEnum)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "当前的队伍是私有的,禁止加入!");
        }
        String password = teamJoinRequest.getPassword();
        if (TeamStatusEnum.SECURE.equals(teamStatusEnum)) {
            if (StringUtils.isBlank(password) || !team.getPassword().equals(password)) {
                throw new BusinessException(ErrorCode.ERROR, "当前队伍密码错误!");
            }
        }
        Long userId = loginUser.getId();
        //该用户已加入的队伍数量
        QueryWrapper<UserTeam> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("'user_id'", userId);
        long hasJoinNum = userTeamService.count(teamQueryWrapper);
        if (hasJoinNum > 5) {
            throw new BusinessException(ErrorCode.ERROR, "最多创建和加入5个队伍");
        }
        //不能重复加入已加入的队伍
        teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("user_id", userId);
        teamQueryWrapper.eq("team_id", teamId);
        long hasUserJoinTeam = userTeamService.count(teamQueryWrapper);
        if (hasUserJoinTeam > 0) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "您已经加入的此队伍!");
        }
        //已加入的队伍人数
        long teamHasJoinNum = this.countTeamUserByTeamId(teamId);
        if (teamHasJoinNum >= team.getMaxNumber()) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "队伍已满");
        }
        //修改队伍信息
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(userId);
        userTeam.setTeamId(teamId);
        userTeam.setJoinTime(new Date());
        return userTeamService.save(userTeam);
    }

    @Override
    public boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser) {
        if (teamQuitRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long teamId = teamQuitRequest.getTeamId();
        if (teamId == null || teamId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = this.getById(teamId);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍不存在!");
        }
        Long userId = loginUser.getId();
        UserTeam queryUserTeam = new UserTeam();
        queryUserTeam.setTeamId(teamId);
        queryUserTeam.setUserId(userId);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>(queryUserTeam);
        long count = userTeamService.count(queryWrapper);
        if (count == 0) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "您未加入此队伍");
        }
        long teamHasJoinNum = this.countTeamUserByTeamId(teamId);
        //队伍仅剩一人，队伍解散
        if (teamHasJoinNum == 1) {
            //删除队伍
            this.removeById(teamId);
        } else {
            //是否为队长
            if (team.getUserId() == userId) {
                //把队伍转移给最早加入的用户
                //1.查询已加入队伍的所有用户和加入时间
                QueryWrapper<UserTeam> teamQueryWrapper = new QueryWrapper<>();
                teamQueryWrapper.eq("team_id", teamId);
                teamQueryWrapper.last("order by id asc limit 2");
                List<UserTeam> userTeamList = userTeamService.list(teamQueryWrapper);
                if (CollectionUtils.isEmpty(userTeamList) || userTeamList.size() == 1) {
                    throw new BusinessException(ErrorCode.SERVER_ERROR);
                }
                UserTeam nextUserTeam = userTeamList.get(1);
                Long nextTeamLeaderId = nextUserTeam.getUserId();
                Team updateTeam = new Team();
                updateTeam.setId(teamId);
                updateTeam.setUserId(nextTeamLeaderId);
                boolean result = this.updateById(updateTeam);
                if (!result) {
                    throw new BusinessException(ErrorCode.SERVER_ERROR, "转移队长失败！");
                }
            }

        }
        //移除队伍关系
        return userTeamService.remove(queryWrapper);
    }

    /**
     * 获取某队伍加入人数
     *
     * @param teamId 队伍ID
     * @return 返回人数
     */
    private long countTeamUserByTeamId(long teamId) {
        QueryWrapper<UserTeam> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("team_id", teamId);
        return userTeamService.count(teamQueryWrapper);
    }
}




