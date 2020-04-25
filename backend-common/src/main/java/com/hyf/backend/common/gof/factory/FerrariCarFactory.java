package com.hyf.backend.common.gof.factory;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class FerrariCarFactory extends AbstractCarFactory {
    @Override
    public void decorateCar(Car car) {
        assert car != null;
        car.setDecorate("最好看的装饰");
    }

    @Override
    public void buyComponent(Car car) {
        assert car != null;
        car.setComponent("最先进的零件");
    }

    @Override
    public Car createCar() {
        Car ferrariCar = new FerrariCar();
        return ferrariCar;
    }
}
