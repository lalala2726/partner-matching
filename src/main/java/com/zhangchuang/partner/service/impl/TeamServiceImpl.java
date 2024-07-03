package com.zhangchuang.partner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangchuang.partner.model.domain.Team;
import com.zhangchuang.partner.service.TeamService;
import com.zhangchuang.partner.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author chuang库操作Service实现
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




