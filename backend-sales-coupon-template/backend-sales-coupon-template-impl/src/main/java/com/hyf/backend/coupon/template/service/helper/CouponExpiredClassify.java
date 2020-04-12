package com.hyf.backend.coupon.template.service.helper;

import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.utils.DateUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/3
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class CouponExpiredClassify {

    private List<UserCouponBO> usableList = new ArrayList<>();
    private List<UserCouponBO> usedList = new ArrayList<>();
    private List<UserCouponBO> expiredList = new ArrayList<>();

    public static CouponExpiredClassify classify(List<UserCouponBO> toCheckCouponList) {
        CouponExpiredClassify couponClassify = new CouponExpiredClassify();
        for (UserCouponBO userCouponBO : toCheckCouponList) {
            //先判断是否过期
            boolean isExpired = false;
            if (userCouponBO.getCouponTemplateBO().getExpirationCode().equals(CouponTemplateExpirationEnum.GUDING.getCode())) {
                //固定有效期
                isExpired = userCouponBO.getCouponTemplateBO().getExpirationDeadline().getTime() <= new Date().getTime();
            } else {
                isExpired = DateUtils.addDay(userCouponBO.getGetTime(), userCouponBO.getCouponTemplateBO().getExpirationGap()).getTime() <= new Date().getTime();
            }

            if (userCouponBO.getStatus().equals(UserCouponStatusEnum.USED.getCode())) {
                couponClassify.getUsedList().add(userCouponBO);
            } else if (userCouponBO.getStatus().equals(UserCouponStatusEnum.EXPIRED.getCode()) || isExpired) {
                couponClassify.getExpiredList().add(userCouponBO);
            } else {
                couponClassify.getUsableList().add(userCouponBO);
            }
        }
        return couponClassify;
    }
}
