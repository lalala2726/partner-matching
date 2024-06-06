package com.zhangchuang.partner;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.zhangchuang.partner.model.domain.User;
import com.zhangchuang.partner.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {


    @Resource
    private UserService userService;


    //查询数据
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        QueryChainWrapper<User> query = userService.query();
        System.out.println(query);

    }


    @Test
    public void testSearchUsersByTags() {
        List<String> tagNameList = Arrays.asList("java","C");
        List<User> users = userService.searchUsersByTags(tagNameList);
        Assert.assertNotNull(users);
    }

}
