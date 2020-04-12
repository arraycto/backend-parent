package com.hyf.backend.portal.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.goods.api.ApiGoodsCategory;
import com.hyf.backend.portal.fallback.ApiCategoryClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(name = "goods-service", path = "/goods-service", fallbackFactory = ApiCategoryClientFallbackFactory.class, configuration = FeignUidInterceptorConfig.class)
public interface ApiCategoryClient extends ApiGoodsCategory {
}
