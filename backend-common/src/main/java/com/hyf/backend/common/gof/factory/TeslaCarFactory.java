package com.hyf.backend.common.gof.factory;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class TeslaCarFactory extends AbstractCarFactory {
    @Override
    public void decorateCar(Car car) {

    }

    @Override
    public void buyComponent(Car car) {

    }

    @Override
    public Car createCar() {
        return new TeslaCar.Builder().setComponent("特斯拉零件").setDecorate("特斯拉装饰").build();
    }
}
