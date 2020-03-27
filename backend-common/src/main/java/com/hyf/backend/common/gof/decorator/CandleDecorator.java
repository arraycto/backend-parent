package com.hyf.backend.common.gof.decorator;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class CandleDecorator implements Sweet {
    private Sweet sweet;

    public CandleDecorator() {

    }

    public CandleDecorator(Sweet sweet) {
        this.sweet = sweet;
    }

    public void setSweet(Sweet sweet) {
        this.sweet = sweet;
    }

    @Override
    public String getDesc() {
        return sweet.getDesc() + ", 蜡烛";
    }

    @Override
    public int cost() {
        return sweet.cost() + 10;
    }
}
