package com.zhangchuang.partner.controller;

import com.zhangchuang.partner.mapper.UserMapper;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.service.UserService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created Zhangchuang on 2024/6/5 19:47
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserMapper userMapper;
    private final UserService userService;

    public TestController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/one")
    public List<User> test() {
        List<String> list = Arrays.asList("oo", "dd");
        return userService.searchUsersByTags(list);
    }

    @GetMapping("/insert")
    public String doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUMBER = 1000000;
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

        return "\"耗时:\" + stopWatch.getLastTaskTimeMillis()";
    }
}
