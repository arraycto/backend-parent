package com.hyf.backend.common.constant;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 有效期类型的枚举
 */
@Getter
public enum ProductLineEnum {
    ACTIVITY_LINE("游学线路", 1);

    private String desc;
    private int code;

    ProductLineEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static ProductLineEnum of(int code) {
        return Stream.of(values()).filter(bean -> bean.getCode() == code).findAny().orElseThrow(() -> new IllegalArgumentException(code + "not exists"));
    }
}
