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

}




