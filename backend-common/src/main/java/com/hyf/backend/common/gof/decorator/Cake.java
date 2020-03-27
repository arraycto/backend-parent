package com.hyf.backend.common.gof.decorator;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Cake implements Sweet{
    @Override
    public String getDesc() {
        return "蛋糕";
    }

    @Override
    public int cost() {
        return 50;
    }
}
