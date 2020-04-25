package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerB extends Handler {
    @Override
    public boolean doHandle(String msg) {
//        if (msg.equals("haha")) {
//            return false;
//        }
        System.out.println("HandlerB: " + msg);
        return true;
    }
}
