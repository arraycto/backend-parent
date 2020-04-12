package com.hyf.backend.coupon.template.service.helper;

import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/11
 * @Email: yfelvis@gmail.com
 * @Desc: 优惠券分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDiscountTypeClassify {

    private List<UserCouponBO> manjianList;
    private List<UserCouponBO> lijianList;
    private List<UserCouponBO> zhekouList;

    public static CouponDiscountTypeClassify classify(List<UserCouponBO> userCouponList) {

        List<UserCouponBO> manjianList = new ArrayList<>();
        List<UserCouponBO> zhekouList = new ArrayList<>();
        List<UserCouponBO> lijianList = new ArrayList<>();
        for (UserCouponBO userCouponBO : userCouponList) {
            if (userCouponBO.getCouponTemplateBO().getDiscountCategory().equals(CouponDiscountCategoryEnum.ZHEKOU.getCode())) {
                zhekouList.add(userCouponBO);
            } else if (userCouponBO.getCouponTemplateBO().getDiscountCategory().equals(CouponDiscountCategoryEnum.LIJIAN.getCode())) {
                lijianList.add(userCouponBO);
            } else {
                manjianList.add(userCouponBO);
            }
        }
        return new CouponDiscountTypeClassify(manjianList, lijianList, zhekouList);
    }

    public UserCouponBO getMaxDiscountFromManjian() {
        if (CollectionUtils.isEmpty(this.manjianList)) {
            return null;
        }
        Integer maxManjianQuota = Integer.MIN_VALUE;
        UserCouponBO maxManjianCoupon = null;
        for (UserCouponBO userCouponBO : this.manjianList) {
            if (userCouponBO.getCouponTemplateBO().getManjianQuota() >= maxManjianQuota) {
                maxManjianQuota = userCouponBO.getCouponTemplateBO().getManjianQuota();
                maxManjianCoupon = userCouponBO;
            }
        }
        return maxManjianCoupon;
    }

    public UserCouponBO getMaxDiscountFromzhekou() {
        if (CollectionUtils.isEmpty(this.zhekouList)) {
            return null;
        }
        Integer maxMaxDiscountQuota = Integer.MIN_VALUE;
        UserCouponBO maxDiscountCoupon = null;
        for (UserCouponBO userCouponBO : this.zhekouList) {
            if (userCouponBO.getCouponTemplateBO().getZhekouQuota() >= maxMaxDiscountQuota) {
                maxMaxDiscountQuota = userCouponBO.getCouponTemplateBO().getZhekouQuota();
                maxDiscountCoupon = userCouponBO;
            }
        }
        return maxDiscountCoupon;
    }

    public UserCouponBO getMaxDiscountFromLijian() {
        if (CollectionUtils.isEmpty(this.lijianList)) {
            return null;
        }
        Integer maxDiscountLijian = Integer.MIN_VALUE;
        UserCouponBO maxLijianCoupon = null;
        for (UserCouponBO userCouponBO : this.lijianList) {
            if (userCouponBO.getCouponTemplateBO().getLijianQuota() >= maxDiscountLijian) {
                maxDiscountLijian = userCouponBO.getCouponTemplateBO().getLijianQuota();
                maxLijianCoupon = userCouponBO;
            }
        }
        return maxLijianCoupon;
    }


}
