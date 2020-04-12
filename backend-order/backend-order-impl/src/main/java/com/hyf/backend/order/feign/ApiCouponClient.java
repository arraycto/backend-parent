package com.hyf.backend.order.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.coupon.template.api.ApiCoupon;
import com.hyf.backend.order.fallback.ApiCouponFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(name = "template-service", path = "/template-service", fallbackFactory = ApiCouponFallbackFactory.class, configuration = FeignUidInterceptorConfig.class)
public interface ApiCouponClient extends ApiCoupon {
}
