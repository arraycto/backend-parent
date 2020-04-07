package com.hyf.backend.admin.feign;

import com.hyf.backend.admin.config.AdminFeignConfig;
import com.hyf.backend.admin.fallback.AdminCouponTemplateFallbackFactory;
import com.hyf.backend.goods.admin.AdminGoodsCategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(value = "goods-service", fallbackFactory = AdminCouponTemplateFallbackFactory.class, path = "/goods-service", configuration = AdminFeignConfig.class)
public interface AdminGoodsCategoryClient extends AdminGoodsCategoryApi {
}
