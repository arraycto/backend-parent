package com.hyf.backend.admin.config;

import com.hyf.backend.common.config.FeignAdminCreateIdInterceptorConfig;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class AdminFeignConfig {
    @Bean
    public RequestInterceptor adminFeignRequestIntereptor() {
        return new FeignAdminCreateIdInterceptorConfig();
    }
}
