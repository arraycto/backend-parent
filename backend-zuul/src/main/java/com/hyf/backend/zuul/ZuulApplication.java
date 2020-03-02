package com.hyf.backend.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringCloudApplication
//开启zuul代理，所有请求都是在HystriCommand中执行
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        //网关集成了Hystrix
        SpringApplication.run(ZuulApplication.class, args);
    }
}
