package com.hyf.backend.coupon.template.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: 优惠券模板基础服务，CRUD
 */
public interface CouponTemplateService {

    CouponTemplateBO createCouponTemplate(AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO);

    PageListBO<CouponTemplateBO> listCouponTemplate(AdminQueryCouponTemplateDTO queryCouponTemplateDTO);
}
