package com.zhangchuang.partner.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chuang
 */
@Component
@Slf4j
public class PreCacheJob {


    private final UserService userService;
    private final RedisTemplate redisTemplate;

    //重点用户
    private List<Long> mainUserList = Arrays.asList(1L);

    public PreCacheJob(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 每天凌晨五点定时刷新用户推荐刷新缓存
     */
    @Scheduled(cron = "0 0 5 * * ? ")
    public void doCacheRecommend() {
        for (Long userId : mainUserList) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
            String redisKey = String.format("user:recommend:%s", userId);
            ValueOperations valueOperations = redisTemplate.opsForValue();
            //写缓存
            try {
                valueOperations.set(redisKey, userPage, 1440, TimeUnit.MINUTES); // 设置10分钟过期时间
            } catch (Exception e) {
                log.error("redis set key error", e);
            }
        }

    }
}
