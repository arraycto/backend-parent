package com.hyf.backend.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Elvis on 2020/2/13
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hyf.backend"})
@MapperScan("com.hyf.backend.user.mapper")
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
