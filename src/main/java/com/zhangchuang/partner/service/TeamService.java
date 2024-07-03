package com.zhangchuang.partner.service;

import com.zhangchuang.partner.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangchuang.partner.model.domain.User;

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

}
