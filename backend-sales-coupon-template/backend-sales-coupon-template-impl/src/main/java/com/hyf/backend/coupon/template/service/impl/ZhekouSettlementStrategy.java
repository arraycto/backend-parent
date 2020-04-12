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
public class ZhekouSettlementStrategy extends BaseSettlementStrategy implements UserCouponSettlementStrategy {
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
        return CouponDiscountCategoryEnum.ZHEKOU;
    }

    @Override
    public UserSettlementBO settlement(UserSettlementDTO userSettlementDTO) {
        BigDecimal costPrice = calcProductCostPrice(userSettlementDTO);
        if (!isProductTypeSatisfy(userSettlementDTO)) {
            return new UserSettlementBO(Collections.emptyList(), costPrice, userSettlementDTO.getEmploy(), costPrice);
        }
        if (CollectionUtils.isNotEmpty(userCouponBOList)) {
            UserCouponBO userCouponBO = userCouponBOList.get(0);
            Integer zhekouQuota = userCouponBO.getCouponTemplateBO().getZhekouQuota();
            BigDecimal discountQuota = costPrice.multiply(BigDecimal.valueOf(zhekouQuota).divide(BigDecimal.valueOf(100)));
            BigDecimal actualPrice = costPrice.subtract(retain2Decimals(discountQuota));
            actualPrice = actualPrice.compareTo(minCost()) < 0 ? minCost() : actualPrice;
//            BigDecimal actualPrice = (retain2Decimals(costPrice.multiply((BigDecimal.valueOf(zhekouQuota).multiply(BigDecimal.valueOf(1.0))).divide(BigDecimal.valueOf(zhekouQuota))))
//                    .compareTo(minCost())) > 0 ? retain2Decimals(costPrice.multiply(BigDecimal.valueOf(1.0).divide(BigDecimal.valueOf(zhekouQuota))))
//                    : minCost();
            UserSettlementBO userSettlementBO = new UserSettlementBO();
            userSettlementBO.setEmploy(userSettlementBO.getEmploy());
            userSettlementBO.setUserCouponBO(userCouponBOList);
            userSettlementBO.setCost(actualPrice);
            userSettlementBO.setTotalGoodsPrice(costPrice);
            return userSettlementBO;
        }
        return new UserSettlementBO(Collections.emptyList(), BigDecimal.ZERO, userSettlementDTO.getEmploy(), costPrice);
    }


}
