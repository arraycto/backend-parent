package com.hyf.backend.distribution.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Elvis on 2020/2/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DistributionCouponApp {
    public static void main(String[] args) {
        SpringApplication.run(DistributionCouponApp.class, args);
    }
}
