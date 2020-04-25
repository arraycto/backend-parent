package com.hyf.backend.common.gof.factory;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class AbstractCarFactory {

    public final Car getCar() {
        Car car = createCar();
        buyComponent(car);
        decorateCar(car);
        return car;
    }

    /**
     * 由每个具体的汽车厂商实现
     *
     * @return
     */
    public abstract void decorateCar(Car car);

    /**
     * 由每个具体的汽车厂商实现
     *
     * @return
     */
    public abstract void buyComponent(Car car);

    /**
     * 由每个具体的汽车厂商实现
     *
     * @return
     */
    public abstract Car createCar();
}
