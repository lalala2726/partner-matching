package com.zhangchuang.partner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chuang
 */
@SpringBootApplication
@MapperScan("com.zhangchuang.partner.mapper")
public class PartnersApplication {

    /**
     * 应用程序的主入口函数。
     *
     * @param args 命令行参数，通常用于传递启动参数给应用程序。
     *             <p>
     *             本函数主要完成以下功能：
     *             1. 使用SpringApplication.run方法启动Spring Boot应用。
     *             2. 打印启动成功的消息。
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(PartnersApplication.class, args);
        // 打印启动成功信息
        System.out.println("  ____            _                         ____  _             _     ____                               __       _ \n" +
                " |  _ \\ __ _ _ __| |_ _ __   ___ _ __ ___  / ___|| |_ __ _ _ __| |_  / ___| _   _  ___ ___ ___  ___ ___ / _|_   _| |\n" +
                " | |_) / _` | '__| __| '_ \\ / _ \\ '__/ __| \\___ \\| __/ _` | '__| __| \\___ \\| | | |/ __/ __/ _ \\/ __/ __| |_| | | | |\n" +
                " |  __/ (_| | |  | |_| | | |  __/ |  \\__ \\  ___) | || (_| | |  | |_   ___) | |_| | (_| (_|  __/\\__ \\__ \\  _| |_| | |\n" +
                " |_|   \\__,_|_|   \\__|_| |_|\\___|_|  |___/ |____/ \\__\\__,_|_|   \\__| |____/ \\__,_|\\___\\___\\___||___/___/_|  \\__,_|_|\n" +
                "                                                                                                                    ");
    }

}
