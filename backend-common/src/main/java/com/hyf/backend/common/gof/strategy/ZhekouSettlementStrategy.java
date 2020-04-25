package com.hyf.backend.common.gof.strategy;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ZhekouSettlementStrategy implements SettlementStrategy {
    @Override
    public String settlement(Integer price) {
        System.out.println("折扣策略: ");
        return "折扣" + price * 0.5;
    }
}
