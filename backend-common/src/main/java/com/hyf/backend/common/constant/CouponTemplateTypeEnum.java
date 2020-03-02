package com.hyf.backend.common.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 优惠券分类
 */
@Getter
public enum CouponTemplateTypeEnum {
    MANJIAN("满减券", "001"),
    LIJIAN("立减券", "002"),
    ZHEKOU("折扣券", "003");
    private String desc;
    private String code;

    CouponTemplateTypeEnum(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public static CouponTemplateTypeEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code)).findAny().orElseThrow(() -> new IllegalArgumentException(code + " not exists"));
    }
}
