package com.test.usercanter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.test.usercanter.mapper")
public class UserCanterApplication {
    // 启动springboot项目
    public static void main(String[] args) {

        SpringApplication.run(UserCanterApplication.class, args);
    }

}
