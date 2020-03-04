package com.hyf.backend.coupon.template.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Elvis on 2020/3/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
//@Configuration
public class FeignClientCustomConfig {
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.BASIC;
    }
}
