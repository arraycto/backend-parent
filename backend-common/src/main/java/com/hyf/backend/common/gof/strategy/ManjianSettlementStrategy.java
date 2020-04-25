package com.hyf.backend.common.gof.strategy;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ManjianSettlementStrategy implements SettlementStrategy {
    @Override
    public String settlement(Integer price) {
        System.out.println("满减策略");
        return price - 20 + "";
    }
}
