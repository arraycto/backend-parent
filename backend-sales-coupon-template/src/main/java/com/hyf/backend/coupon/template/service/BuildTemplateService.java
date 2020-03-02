package com.hyf.backend.coupon.template.service;

import com.hyf.backend.coupon.template.entity.SalesCouponTemplate;
import com.hyf.backend.coupon.template.vo.TemplateCreateRequest;
import com.hyf.backend.utils.exception.BizException;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface BuildTemplateService {

    /**
     * 构建优惠券模板，根据创建的优惠券的数量
     *
     * @param req
     * @return
     * @throws BizException
     */
    SalesCouponTemplate buildCouponTemplate(TemplateCreateRequest req) throws BizException;
}
