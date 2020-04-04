package com.hyf.backend.admin.feign;

import com.hyf.backend.admin.config.AdminFeignConfig;
import com.hyf.backend.admin.fallback.AdminCouponTemplateFallbackFactory;
import com.hyf.backend.coupon.template.admin.AdminCouponTemplateApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(value = "template-service", fallbackFactory = AdminCouponTemplateFallbackFactory.class, path = "/template-service", configuration = AdminFeignConfig.class)
public interface AdminCouponTemplateClient extends AdminCouponTemplateApi {
}
