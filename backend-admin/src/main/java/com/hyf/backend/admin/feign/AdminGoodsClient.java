package com.hyf.backend.admin.feign;

import com.hyf.backend.admin.config.AdminFeignConfig;
import com.hyf.backend.admin.fallback.AdminCouponTemplateFallbackFactory;
import com.hyf.backend.goods.admin.AdminGoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(value = "goods-service", fallbackFactory = AdminCouponTemplateFallbackFactory.class, path = "/goods-service", configuration = AdminFeignConfig.class)
public interface AdminGoodsClient extends AdminGoodsApi {

}
