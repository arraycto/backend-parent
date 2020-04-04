package com.hyf.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@AllArgsConstructor
@Getter
public enum ProductType {

    WENYU("文娱", 1),
    SHENGXIAN("生鲜", 2),
    JIAJU("家居", 3),
    OTHERS("其他", 4),
    ALL("全品类", 5);

    /**
     * 商品类型描述
     */
    private String description;

    /**
     * 商品类型编码
     */
    private Integer code;

    public static ProductType of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists!")
                );
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
