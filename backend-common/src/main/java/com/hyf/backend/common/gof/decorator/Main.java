package com.hyf.backend.common.gof.decorator;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Main {
    public static void main(String[] args) {
        Cake cake = new Cake();
        FruitDecorator fruitDecorator = new FruitDecorator(cake);
        CandleDecorator candleDecorator = new CandleDecorator(fruitDecorator);
        System.out.println(candleDecorator.getDesc() + " " + candleDecorator.cost());
    }
}
