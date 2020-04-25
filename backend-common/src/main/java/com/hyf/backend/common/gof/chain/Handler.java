package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class Handler {
    private Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public boolean handle(String msg) {
        boolean isContinue = doHandle(msg);
        if (!isContinue) {
            return isContinue;
        }
        if (nextHandler != null) {
            nextHandler.handle(msg);
        }
        return true;
    }

    public abstract boolean doHandle(String msg);
}
