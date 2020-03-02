package com.hyf.backend.distribution.coupon.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: Elvis on 2020/2/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Getter
public enum CouponStatusEnum {

    USABLE("可用的", 0),
    USED("已使用的", 1),
    EXPIRED("过期的", 2);
    private String desc;
    private Integer code;

    CouponStatusEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public CouponStatusEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Arrays.stream(values()).filter(obj -> obj.getCode().equals(code)).findAny().orElseThrow(() -> new IllegalArgumentException(code + " not exists"));
    }

}
