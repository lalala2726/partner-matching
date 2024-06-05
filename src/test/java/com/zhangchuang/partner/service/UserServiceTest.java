package com.zhangchuang.partner.service;

import com.zhangchuang.partner.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created Zhangchuang on 2024/5/28 4:18
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("zhangchuang");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("");
        user.setUserPassword("123");
        boolean save = userService.save(user);
        System.out.println("创建"+save);
    }

    @Test
    public void userRegister(){
//        long id = userService.userRegister("zhangchuang", "12345678", "12345678","2314");
//        System.out.println("注册"+id);
    }

}
