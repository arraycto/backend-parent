package com.hyf.backend.common.gof.decorator;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: 装饰者和被装饰着需要实现相同的接口或者继承相同的抽象，目的不是为了获得某种行为，而是固定了类型
 */
public class FruitDecorator implements Sweet {
    private Sweet sweet;

    public FruitDecorator() {

    }

    public FruitDecorator(Sweet sweet) {
        this.sweet = sweet;
    }

    public void setSweet(Sweet sweet) {
        this.sweet = sweet;
    }

    @Override
    public String getDesc() {
        return sweet.getDesc() + ", 水果";
    }

    @Override
    public int cost() {
        return sweet.cost() + 10;
    }
}
