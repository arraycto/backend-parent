package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Elvis on 2020/2/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {
    /**
     * 根据模板异步创建优惠券码
     *
     * @param salesCouponTemplate
     */
    @Override
    public void asyncGenerateKeyByCouponTemplate(CouponTemplateDO salesCouponTemplate) {

    }
}
