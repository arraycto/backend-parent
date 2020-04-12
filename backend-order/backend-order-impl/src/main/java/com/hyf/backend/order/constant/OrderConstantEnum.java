package com.hyf.backend.order.constant;

import com.hyf.backend.utils.exception.BizException;

import java.util.stream.Stream;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public enum OrderConstantEnum {
    CREATE("未付款", 0),
    PAIED("已支付", 1),
    CANCEL("已取消", 2);

    private String desc;
    private Integer code;

    OrderConstantEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public static OrderConstantEnum of(Integer code) {
        return Stream.of(values()).filter(bean -> bean.getCode().equals(code)).findFirst().orElseThrow(() -> new BizException("没有相关状态"));
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
