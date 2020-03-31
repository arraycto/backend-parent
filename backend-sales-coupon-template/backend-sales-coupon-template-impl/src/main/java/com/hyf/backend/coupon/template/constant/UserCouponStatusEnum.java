package com.hyf.backend.coupon.template.constant;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public enum UserCouponStatusEnum {
    USED("已使用的", 0),
    USABLE("可用的", 1),
    EXPIRED("过期的", 2);

    private String desc;
    private Integer code;

    UserCouponStatusEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public static UserCouponStatusEnum of(Integer code) {
        Objects.requireNonNull(code);
        UserCouponStatusEnum userCouponStatusEnum = Stream.of(values()).filter(c -> c.code.equals(code)).findFirst().orElseThrow(() -> new IllegalArgumentException("找不到" + code + "枚举"));
        return userCouponStatusEnum;
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
