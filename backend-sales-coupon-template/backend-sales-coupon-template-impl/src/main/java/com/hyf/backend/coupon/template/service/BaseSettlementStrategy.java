package com.hyf.backend.coupon.template.service;

import com.hyf.backend.coupon.template.api.dto.ProductDTO;
import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class BaseSettlementStrategy {

    /**
     * 默认是但优惠券消费的
     *
     * @param userSettlementDTO
     * @return
     */
    protected boolean isProductTypeSatisfy(UserSettlementDTO userSettlementDTO) {
        List<Integer> productTypeList = userSettlementDTO.getProductDTOList().stream().map(ProductDTO::getType).collect(Collectors.toList());
        List<UserCouponBO> userCouponList = getUserCouponList(userSettlementDTO);
        List<List<Integer>> collect = userCouponList.stream().map(coupon -> {
            CouponTemplateBO couponTemplateBO = coupon.getCouponTemplateBO();
            return couponTemplateBO.getGoodsTypeLimitation();
        }).collect(Collectors.toList());
        List<Integer> goodsTypeLimitationList = collect.stream().flatMap(Collection::stream).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(CollectionUtils.intersection(productTypeList, goodsTypeLimitationList));
    }

    protected BigDecimal calcProductCostPrice(UserSettlementDTO userSettlementDTO) {
        BigDecimal cost = BigDecimal.ZERO;
        for (ProductDTO productDTO : userSettlementDTO.getProductDTOList()) {
            cost.add(productDTO.getPrice().multiply(BigDecimal.valueOf(productDTO.getCount())));
        }
        return cost;
    }

    protected BigDecimal minCost() {
        return BigDecimal.valueOf(0.1);
    }
    protected BigDecimal retain2Decimals(BigDecimal value) {

        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    protected abstract List<UserCouponBO> getUserCouponList(UserSettlementDTO userSettlementDTO);
}
