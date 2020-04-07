package com.hyf.backend.admin.feign;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

import com.hyf.backend.admin.config.AdminFeignConfig;
import com.hyf.backend.admin.fallback.AdminCouponTemplateFallbackFactory;
import com.hyf.backend.coupon.template.admin.AdminBannerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "template-service", fallbackFactory = AdminCouponTemplateFallbackFactory.class, path = "/template-service", configuration = AdminFeignConfig.class)
public interface AdminBannerClient extends AdminBannerApi {
}
