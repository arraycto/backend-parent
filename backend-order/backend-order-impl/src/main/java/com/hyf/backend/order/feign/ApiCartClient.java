package com.hyf.backend.order.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.goods.api.ApiCart;
import com.hyf.backend.order.fallback.ApiGoodsClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(name = "goods-service", path = "/goods-service", fallbackFactory = ApiGoodsClientFallbackFactory.class, configuration = FeignUidInterceptorConfig.class)
public interface ApiCartClient extends ApiCart {
}
