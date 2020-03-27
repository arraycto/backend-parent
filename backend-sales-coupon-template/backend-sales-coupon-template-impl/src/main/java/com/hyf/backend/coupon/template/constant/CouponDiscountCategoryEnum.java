package com.hyf.backend.coupon.template.constant;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: 优惠券折扣分类
 */
public enum CouponDiscountCategoryEnum {
    MANJIAN("满减", 0),
    LIJIAN("立减", 1),
    ZHEKOU("折扣", 2);

    private String desc;
    private Integer code;

    CouponDiscountCategoryEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public static CouponDiscountCategoryEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code)).findAny().orElseThrow(() -> new IllegalArgumentException(code + " Not Exists"));
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
