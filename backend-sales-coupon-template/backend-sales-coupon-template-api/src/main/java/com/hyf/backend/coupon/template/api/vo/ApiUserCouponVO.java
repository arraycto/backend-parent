package com.hyf.backend.coupon.template.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserCouponVO {
    private Long id;

    private Long templateId;

    private Long userId;

    private String couponCode;

    private Date getTime;

    private Integer status;

    private ApiCouponTemplateVO couponTemplate = new ApiCouponTemplateVO();

    private Integer effectDays;

    private Long startTime;
    private Long endTime;
}
