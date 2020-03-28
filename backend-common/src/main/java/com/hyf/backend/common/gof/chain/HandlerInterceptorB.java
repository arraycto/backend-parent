package com.hyf.backend.common.gof.chain;

import java.lang.reflect.Method;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerInterceptorB implements HandlerInterceptor {
    @Override
    public boolean applyPreHandler(String msg, Method method) {
        System.out.println(this.getClass().getName() + "msg: " + msg + " method: " + method.getName());
        return true;
    }

    @Override
    public void applyPostHandler(String msg, Method method) {
        System.out.println(this.getClass().getName() + "msg: " + msg + " method: " + method.getName());
    }

    @Override
    public void afterCompletion(String msg, Method method, Throwable e) {
        System.out.println(this.getClass().getName() + "msg: " + msg + " method: " + method.getName());
    }
}
