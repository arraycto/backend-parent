package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.bo.UserSettlementBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.service.BaseSettlementStrategy;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.coupon.template.service.UserCouponSettlementStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class LijianSettlementStrategy extends BaseSettlementStrategy implements UserCouponSettlementStrategy {
    private List<UserCouponBO> userCouponBOList;
    @Autowired
    private UserCouponService userCouponService;

    @Override
    protected List<UserCouponBO> getUserCouponList(UserSettlementDTO userSettlementDTO) {
        userCouponBOList = userCouponService.getUserCouponByIdList(userSettlementDTO.getUserCouponIdList());
        return userCouponBOList;
    }

    @Override
    public CouponDiscountCategoryEnum getDiscountCategory() {
        return CouponDiscountCategoryEnum.LIJIAN;
    }

    @Override
    public UserSettlementBO settlement(UserSettlementDTO userSettlementDTO) {
        boolean productTypeSatisfy = isProductTypeSatisfy(userSettlementDTO);
        BigDecimal costPrice = calcProductCostPrice(userSettlementDTO);
        if (!productTypeSatisfy) {
            return new UserSettlementBO(Collections.emptyList(), costPrice, userSettlementDTO.getEmploy());
        }
        if (CollectionUtils.isNotEmpty(userCouponBOList)) {
            UserCouponBO userCouponBO = userCouponBOList.get(0);
            Integer lijianQuota = userCouponBO.getCouponTemplateBO().getLijianQuota();
            BigDecimal actualPrice = costPrice.subtract(BigDecimal.valueOf(lijianQuota));
            UserSettlementBO userSettlementBO = new UserSettlementBO();
            userSettlementBO.setCost(actualPrice.compareTo(minCost()) < 0 ? minCost() : actualPrice);
            userSettlementBO.setUserCouponBO(userCouponBOList);
            userSettlementBO.setEmploy(userSettlementDTO.getEmploy());
            return userSettlementBO;
        }
        return new UserSettlementBO(Collections.emptyList(), BigDecimal.ZERO, userSettlementDTO.getEmploy());

    }
}
