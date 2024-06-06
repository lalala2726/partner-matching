package com.zhangchuang.partner.controller;

import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.service.UserService;
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

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/one")
    public List<User> test() {
        List<String> list = Arrays.asList("oo", "dd");
        return userService.searchUsersByTags(list);
    }
}
