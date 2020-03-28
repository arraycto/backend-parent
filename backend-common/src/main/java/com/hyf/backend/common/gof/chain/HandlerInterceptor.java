package com.hyf.backend.common.gof.chain;

import java.lang.reflect.Method;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface HandlerInterceptor {
    boolean applyPreHandler(String msg, Method method);

    void applyPostHandler(String msg, Method method);

    void afterCompletion(String msg, Method method, Throwable e);
}
