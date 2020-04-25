package com.hyf.backend.common.gof.chain;

import java.lang.reflect.Method;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Invocation {
    private final Object target;
    private final Method method;
    private final Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws Exception {
        return method.invoke(target, args);
    }
}
