package com.hyf.backend.common.constant;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Getter
public enum PeriodTypeEnum {
    REGULAR("固定的日期", 1),
    SHIFT("变动的日期比如从领取开始7天内有效", 2);

    private String desc;
    private int code;

    PeriodTypeEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static PeriodTypeEnum of(int code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code == code).findAny().orElseThrow(() -> new IllegalArgumentException(code + "not exists"));
    }
}
