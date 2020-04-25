package com.hyf.backend.common.gof.factory;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class FerrariCar extends Car {
    private String component;
    private String decorate;
    @Override
    void play() {
        System.out.println("decorate: " + decorate);
    }

    @Override
    void show() {
        System.out.println("我有零件 " + component);
    }

    @Override
    void setComponent(String component) {
        this.component = component;
    }

    @Override
    void setDecorate(String decorate) {
        this.decorate = decorate;
    }
}
