package com.hyf.backend.portal.feign;

import com.hyf.backend.common.config.FeignUidInterceptorConfig;
import com.hyf.backend.goods.api.ApiGoods;
import com.hyf.backend.goods.api.ApiTopic;
import com.hyf.backend.portal.fallback.ApiCategoryClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(name = "goods-service", path = "/goods-service", fallbackFactory = ApiCategoryClientFallbackFactory.class, configuration = FeignUidInterceptorConfig.class)
public interface ApiTopicClient extends ApiTopic {
}
