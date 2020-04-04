package com.hyf.backend.coupon.template.service;

import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.bo.UserSettlementBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserCouponSettlementStrategy {

    CouponDiscountCategoryEnum getDiscountCategory();

    UserSettlementBO settlement(UserSettlementDTO userSettlementDTO);

}
