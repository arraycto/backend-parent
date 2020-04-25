package com.hyf.backend.common.gof.factory;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Main {
    public static void main(String[] args) {
        AbstractCarFactory carFactory = new FerrariCarFactory();
        Car car = carFactory.getCar();
        car.play();
        car.show();

        Car teslaCar = new TeslaCarFactory().createCar();
        teslaCar.show();
        teslaCar.play();
    }
}
