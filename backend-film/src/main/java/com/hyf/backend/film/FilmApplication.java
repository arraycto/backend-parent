package com.hyf.backend.film;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Elvis on 2020/2/15
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.hyf.backend.film.mapper"})
@ComponentScan(basePackages = {"com.hyf.backend"})
public class FilmApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilmApplication.class, args);
    }
}
