package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerA extends Handler{

    @Override
    public boolean doHandle(String msg) {
        System.out.println("handlerA " + msg);
        return true;
    }
}
