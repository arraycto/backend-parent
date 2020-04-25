package com.hyf.backend.common.gof.factory;

import org.springframework.beans.BeanUtils;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class TeslaCar extends Car {
    private String component;
    private String decorate;

    private TeslaCar(Builder builder) {
        this.component = builder.component;
        this.decorate
                 = builder.decorate;
//        BeanUtils.copyProperties(builder, this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String component;
        private String decorate;

        public Builder setComponent(String component) {
            this.component = component;
            return this;
        }

        public Builder setDecorate(String decorate) {
            this.decorate = decorate;
            return this;
        }

        public TeslaCar build() {
            return new TeslaCar(this);
        }
    }

    @Override
    void play() {
        System.out.println("component: " + component);
    }

    @Override
    void show() {
        System.out.println("decorate: " + decorate);
    }

    @Override
    void setComponent(String component) {

    }

    @Override
    void setDecorate(String decorate) {

    }

    public String getComponent() {
        return component;
    }

    public String getDecorate() {
        return decorate;
    }
}
