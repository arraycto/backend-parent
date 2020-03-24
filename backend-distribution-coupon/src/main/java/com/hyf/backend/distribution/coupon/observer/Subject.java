package com.hyf.backend.distribution.coupon.observer;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface Subject {
    void add(Observer observer);
    void remove(Observer observer);
    void notifyObserver(String msg);
}
