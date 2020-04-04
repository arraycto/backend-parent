package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.bo.UserSettlementBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.service.BaseSettlementStrategy;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.coupon.template.service.UserCouponSettlementStrategy;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
@Slf4j
public class ManjianSettlementStrategy extends BaseSettlementStrategy implements UserCouponSettlementStrategy {
    @Autowired
    private UserCouponService userCouponService;
    private List<UserCouponBO> userCouponBOList;

    @Override
    public CouponDiscountCategoryEnum getDiscountCategory() {
        return CouponDiscountCategoryEnum.MANJIAN;
    }

    @Override
    public UserSettlementBO settlement(UserSettlementDTO userSettlementDTO) {
        boolean productTypeSatisfy = isProductTypeSatisfy(userSettlementDTO);
        if (!productTypeSatisfy) {
            throw new BizException("商品类型和优惠券所需要的类型不匹配");
        }
        if (CollectionUtils.isNotEmpty(userCouponBOList)) {
            UserSettlementBO userSettlementBO = new UserSettlementBO();
            userSettlementBO.setEmploy(userSettlementBO.getEmploy());
            //单品类的
            UserCouponBO userCouponBO = userCouponBOList.get(0);
            userSettlementBO.setUserCouponBO(Collections.singletonList(userCouponBO));
            Integer discountBase = userCouponBO.getCouponTemplateBO().getDiscountBase();
            BigDecimal costPrice = calcProductCostPrice(userSettlementDTO);
            if (costPrice.compareTo(BigDecimal.valueOf(discountBase)) < 0) {
                //比base小，不足满减的基准
                log.error("没有达到满减的基准 base: {}", discountBase);
                return new UserSettlementBO(Collections.emptyList(), BigDecimal.ZERO, userSettlementDTO.getEmploy());
            }

            BigDecimal actualPrice = costPrice.subtract(BigDecimal.valueOf(userCouponBO.getCouponTemplateBO().getManjianQuota()));
            userSettlementBO.setCost(actualPrice.compareTo(minCost()) < 0 ? minCost() : actualPrice);
            return userSettlementBO;
        }

        return new UserSettlementBO(Collections.emptyList(), BigDecimal.ZERO, userSettlementDTO.getEmploy());


    }

    @Override
    protected List<UserCouponBO> getUserCouponList(UserSettlementDTO userSettlementDTO) {
        userCouponBOList = userCouponService.getUserCouponByIdList(userSettlementDTO.getUserCouponIdList());
        return userCouponBOList;
    }
}
