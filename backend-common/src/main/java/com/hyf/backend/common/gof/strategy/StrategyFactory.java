package com.hyf.backend.common.gof.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public enum StrategyFactory {
    INSTANCE;
    private static final Map<String, SettlementStrategy> settlementStrategyMap = new HashMap<>();

    static {
        settlementStrategyMap.put("zhekou", new ZhekouSettlementStrategy());
        settlementStrategyMap.put("manjian", new ManjianSettlementStrategy());
    }

    public static SettlementStrategy getStrategy(String type){
        return settlementStrategyMap.get(type);
    }
}
