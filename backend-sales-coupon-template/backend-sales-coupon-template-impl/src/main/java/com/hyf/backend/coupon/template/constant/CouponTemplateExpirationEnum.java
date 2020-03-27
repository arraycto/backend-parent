package com.hyf.backend.coupon.template.constant;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public enum CouponTemplateExpirationEnum {
    GUDING("固定有效期", 0),
    SHIFT("变动的", 1);

    private String desc;
    private Integer code;

    CouponTemplateExpirationEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public static CouponTemplateExpirationEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code)).findAny().orElseThrow(() -> new IllegalArgumentException(code + " not exists"));
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
