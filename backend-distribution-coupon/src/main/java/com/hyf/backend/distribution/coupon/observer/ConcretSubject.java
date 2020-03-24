package com.hyf.backend.distribution.coupon.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ConcretSubject implements Subject {
    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObserver(String msg) {
        for (Observer observer : observerList) {
            observer.update(msg);
        }
    }

    public static void main(String[] args) {
        Subject subject = new ConcretSubject();
        Observer one= new ConcreatObserverOne();
        Observer two = new ConcreatObserverTwo();
        subject.add(one);
        subject.add(two);
        subject.notifyObserver("我变更了状态");
    }
}
