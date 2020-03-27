package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/3/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerB extends Handler {
    @Override
    protected void doHandle() {
        System.out.println("B责任链执行");
    }
}
