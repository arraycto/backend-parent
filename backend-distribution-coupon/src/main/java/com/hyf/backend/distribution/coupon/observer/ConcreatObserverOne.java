package com.hyf.backend.distribution.coupon.observer;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ConcreatObserverOne implements Observer {
    @Override
    public void update(String msg) {
        System.out.println(this.getClass().getName() + ": " + msg);
    }
}
