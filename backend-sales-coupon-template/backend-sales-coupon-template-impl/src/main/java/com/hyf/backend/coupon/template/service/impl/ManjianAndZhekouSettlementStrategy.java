package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.api.dto.ProductDTO;
import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.bo.UserSettlementBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.service.BaseSettlementStrategy;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.coupon.template.service.UserCouponSettlementStrategy;
import com.hyf.backend.utils.exception.BizException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class ManjianAndZhekouSettlementStrategy extends BaseSettlementStrategy implements UserCouponSettlementStrategy {

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
        return CouponDiscountCategoryEnum.MANJIAN_AND_ZHEKOU;
    }

    @Override
    public UserSettlementBO settlement(UserSettlementDTO userSettlementDTO) {
        BigDecimal costPrice = calcProductCostPrice(userSettlementDTO);
        UserCouponBO manjian = null;
        UserCouponBO zhekou = null;
        for (UserCouponBO userCouponBO : this.userCouponBOList) {
            Integer discountCategory = userCouponBO.getCouponTemplateBO().getDiscountCategory();
            if (CouponDiscountCategoryEnum.MANJIAN.getCode().equals(discountCategory)) {
                manjian = userCouponBO;
            } else if (CouponDiscountCategoryEnum.ZHEKOU.getCode().equals(discountCategory)) {
                zhekou = userCouponBO;
            }
        }

        assert manjian != null && zhekou != null;
        if (!isProductTypeSatisfy(userSettlementDTO)) {
            return new UserSettlementBO(Collections.emptyList(), costPrice, userSettlementDTO.getEmploy(), costPrice);
        }

        //校验是否可以共存的限制
        if (!isShared(manjian, zhekou)) {
            return new UserSettlementBO(Collections.emptyList(), costPrice, userSettlementDTO.getEmploy(), costPrice);
        }

        //先计算满减的
        List<UserCouponBO> actualCouponList = new ArrayList<>();
        Integer discountBase = manjian.getCouponTemplateBO().getDiscountBase();
        if (BigDecimal.valueOf(discountBase).compareTo(costPrice) < 0) {
            actualCouponList.add(manjian);
            costPrice = costPrice.subtract(BigDecimal.valueOf(discountBase));
        }

        //计算折扣的
        Integer zhekouQuota = zhekou.getCouponTemplateBO().getZhekouQuota();
        BigDecimal divide = costPrice.multiply(BigDecimal.valueOf(1.0).multiply(BigDecimal.valueOf(zhekouQuota))).divide(BigDecimal.valueOf(100));
        BigDecimal actualPrice = retain2Decimals(divide).compareTo(minCost()) < 0 ? minCost() : retain2Decimals(divide);
        actualCouponList.add(zhekou);

        UserSettlementBO userSettlementBO = new UserSettlementBO();
        userSettlementBO.setCost(actualPrice);
        userSettlementBO.setUserCouponBO(actualCouponList);
        userSettlementBO.setEmploy(userSettlementDTO.getEmploy());
        return userSettlementBO;
    }

    private boolean isShared(UserCouponBO manjian, UserCouponBO zhekou) {


        //能和满减共存的优惠券
        List<String> allManjianSharedKey = new ArrayList<>();
        allManjianSharedKey.add(manjian.getCouponTemplateBO().getTemplateKey());
        allManjianSharedKey.addAll(manjian.getCouponTemplateBO().getWeight());

        List<String> allZhekouSharedKey = new ArrayList<>();
        allZhekouSharedKey.add(zhekou.getCouponTemplateBO().getTemplateKey());
        allZhekouSharedKey.addAll(zhekou.getCouponTemplateBO().getWeight());

        return CollectionUtils.isSubCollection(Arrays.asList(manjian.getCouponTemplateBO().getTemplateKey(), zhekou.getCouponTemplateBO().getTemplateKey()), allManjianSharedKey)
                || CollectionUtils.isSubCollection(Arrays.asList(manjian.getCouponTemplateBO().getTemplateKey(), zhekou.getCouponTemplateBO().getTemplateKey()), allZhekouSharedKey);
    }

    @Override
    protected boolean isProductTypeSatisfy(UserSettlementDTO userSettlementDTO) {
        List<UserCouponBO> userCouponList = getUserCouponList(userSettlementDTO);
        //两张优惠券类型不能相同

        UserCouponBO userCouponBO1 = userCouponList.get(0);
        UserCouponBO userCouponBO2 = userCouponList.get(1);
        if (userCouponBO1.getCouponTemplateBO().getDiscountCategory().equals(userCouponBO2.getCouponTemplateBO().getDiscountCategory())) {
            throw new BizException("两张优惠券类型不能相同");
        }
        //两张优惠券的商品限制
        List<Integer> goodsTypeLimitation = userCouponList.stream().map(userCouponBO -> {
            CouponTemplateBO couponTemplateBO = userCouponBO.getCouponTemplateBO();
            return couponTemplateBO.getGoodsTypeLimitation();
        }).flatMap(Collection::stream).collect(Collectors.toList());

        List<Integer> productTypeList = userSettlementDTO.getProductDTOList().stream().map(ProductDTO::getType).collect(Collectors.toList());

        //优惠券叠加使用，商品必须包含在优惠券允许的范围之内
        return CollectionUtils.isEmpty(CollectionUtils.subtract(productTypeList, goodsTypeLimitation));
    }
}
