package com.hyf.backend.portal.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.coupon.template.api.ApiBanner;
import com.hyf.backend.portal.fallback.ApiBannerClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(name = "template-service", fallbackFactory = ApiBannerClientFallbackFactory.class, path = "/template-service", configuration = FeignUidInterceptorConfig.class)
public interface ApiBannerClient extends ApiBanner {
}
