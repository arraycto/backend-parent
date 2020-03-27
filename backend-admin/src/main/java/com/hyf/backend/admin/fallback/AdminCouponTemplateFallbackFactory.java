package com.hyf.backend.admin.fallback;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.admin.feign.AdminCouponTemplateClient;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class AdminCouponTemplateFallbackFactory implements FallbackFactory<AdminCouponTemplateClient> {
    @Override
    public AdminCouponTemplateClient create(Throwable cause) {
        return new AdminCouponTemplateClient() {
            @Override
            public ResponseVO createCouponTemplate(AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO) {
                log.error("调用优惠券模板服务出错啦...params: {}", JSON.toJSONString(adminCreateCouponTemplateDTO), cause);
                return ResponseVO.error(cause.getMessage());
            }

            @Override
            public ResponseVO listCouponTemplate(AdminQueryCouponTemplateDTO queryCouponTemplateDTO) {
                log.error("调用优惠券模板服务出错啦...params: {}", JSON.toJSONString(queryCouponTemplateDTO), cause);
                return ResponseVO.error(cause.getMessage());
            }
        };
    }
}
