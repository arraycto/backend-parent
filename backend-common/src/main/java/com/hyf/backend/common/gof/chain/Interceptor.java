package com.hyf.backend.common.gof.chain;

import org.apache.ibatis.plugin.Plugin;

import java.util.Properties;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface Interceptor {
    Object intercept(Invocation invocation) throws Throwable;

    default Object plugin(Object target) {
//        return Plugin.wrap(target, this);
        return null;
    }

    default void setProperties(Properties properties) {
        // NOP
    }
}
