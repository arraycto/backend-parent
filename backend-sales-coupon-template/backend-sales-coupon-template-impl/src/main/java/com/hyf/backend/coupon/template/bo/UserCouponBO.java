package com.hyf.backend.coupon.template.bo;

import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.coupon.template.dataobject.UserCouponDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponBO implements Serializable {

    private Long id;

    private Long templateId;

    private Long userId;

    private String couponCode;

    private Date getTime;

    private UserCouponStatusEnum status;

    private CouponTemplateBO couponTemplateBO;

    private Integer effectDays;


    public UserCouponBO(UserCouponDO userCouponDO) {
        BeanUtils.copyProperties(userCouponDO, this);
    }
}
