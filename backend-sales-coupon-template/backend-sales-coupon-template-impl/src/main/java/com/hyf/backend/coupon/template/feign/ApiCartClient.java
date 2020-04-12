package com.hyf.backend.coupon.template.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.coupon.template.fallback.GoodsFallbackFactory;
import com.hyf.backend.goods.api.ApiCart;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(value = "goods-service", path = "/goods-service", fallbackFactory = GoodsFallbackFactory.class, configuration = FeignUidInterceptorConfig.class)
public interface ApiCartClient extends ApiCart {
}
