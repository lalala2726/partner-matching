package com.zhangchuang.partner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhangchuang.partner.mapper")
public class UserCenterApplication {

    /**
     * 应用程序的主入口函数。
     *
     * @param args 命令行参数，通常用于传递启动参数给应用程序。
     * <p>
     * 本函数主要完成以下功能：
     * 1. 使用SpringApplication.run方法启动Spring Boot应用。
     * 2. 打印启动成功的消息。
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(UserCenterApplication.class, args);
        // 打印启动成功信息
        System.out.println("😀用户中心项目启动成功!");
    }

}
