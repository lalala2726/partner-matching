package com.zhangchuang.partner.once;

import java.util.Date;

import com.zhangchuang.partner.mapper.UserMapper;
import com.zhangchuang.partner.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author chuang
 */
@Component
public class InsertUsers {


    @Autowired
    private UserMapper userMapper;


    /**
     * 批量插入用户数据
     */
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUMBER = 1000;
        for (int i = 0; i < INSERT_NUMBER; i++) {
            User user = new User();
            user.setUsername("假用户" + INSERT_NUMBER);
            user.setUserAccount("fakeUser" + INSERT_NUMBER);
            user.setTags("[]");
            user.setAvatarUrl("https://img2.baidu.com/it/u=1790834130,1952230725&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
            user.setGender(0);
            user.setRole(0);
            user.setUserPassword("12345678");
            user.setPhone("12345");
            user.setEmail("11111");
            user.setUserStatus(0);
            user.setPlanetId("11");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println("耗时:" + stopWatch.getLastTaskTimeMillis());

    }

    public static void main(String[] args) {
        new InsertUsers().doInsertUsers();
    }
}
