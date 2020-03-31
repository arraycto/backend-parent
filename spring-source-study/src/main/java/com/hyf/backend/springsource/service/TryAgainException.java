package com.hyf.backend.springsource.service;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class TryAgainException extends RuntimeException {
    public TryAgainException(String msg) {
        super(msg);
    }
}
