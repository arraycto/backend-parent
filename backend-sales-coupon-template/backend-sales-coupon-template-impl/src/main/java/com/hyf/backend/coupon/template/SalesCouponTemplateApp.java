package com.hyf.backend.coupon.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan(basePackages = {"com.hyf.backend.coupon.template.mapper"})
@EnableFeignClients(basePackages = {"com.hyf.backend.coupon.template.feign"})
@EnableCircuitBreaker
//@EnableApolloConfig
public class SalesCouponTemplateApp {
    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
//        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
//        String random = RandomStringUtils.random(1, bases);
//        //生成8位随机字符串
//        String s = RandomStringUtils.randomAlphabetic(8);
////        System.out.println(s);
////        System.out.println(random);
//
//        long l = RandomUtils.nextLong(1, 100);
//        System.out.println(l);
        System.setProperty("env", "DEV");
        SpringApplication.run(SalesCouponTemplateApp.class, args);
//        for (int i = 0; i < 100; i++) {
//            int j = i;
//            pool.execute(new Thread(new Runnable() {
//                @Overri   de
//                public void run() {
//                    SalesCouponTemplateApp.threadLocal.set("猿天地" + j);
//                    //线程池里面的线程会又用线程池里面的线程执行任务
//                    new Service().call();
//                }
//            }));
//        }
    }
}

//class Service {
//    public void call() {
//        SalesCouponTemplateApp.pool.execute(new Runnable() {
//            @Override
//            public void run() {
//                new Dao().call();
//            }
//        });
//    }
//}
//
//class Dao {
//    public void call() {
//        System.out.println("Dao:" + SalesCouponTemplateApp.threadLocal.get());
//    }
//}