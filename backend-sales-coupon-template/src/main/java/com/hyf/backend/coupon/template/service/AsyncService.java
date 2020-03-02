package com.hyf.backend.coupon.template.service;

import com.hyf.backend.coupon.template.entity.SalesCouponTemplate;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AsyncService {

    /**
     * 异步的构造优惠券码
     */
    void asyncGenerateKeyByCouponTemplate(SalesCouponTemplate salesCouponTemplate);
}
