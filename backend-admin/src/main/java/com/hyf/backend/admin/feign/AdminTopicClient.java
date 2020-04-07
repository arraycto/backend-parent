package com.hyf.backend.admin.feign;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

import com.hyf.backend.admin.config.AdminFeignConfig;
import com.hyf.backend.admin.fallback.AdminCouponTemplateFallbackFactory;
import com.hyf.backend.coupon.template.admin.AdminBannerApi;
import com.hyf.backend.goods.admin.AdminMallTopicApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "goods-service", fallbackFactory = AdminCouponTemplateFallbackFactory.class, path = "/goods-service", configuration = AdminFeignConfig.class)
public interface AdminTopicClient extends AdminMallTopicApi {
}
