package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/3/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class Handler {
    protected Handler successor = null;
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
    public final void handle() {
        doHandle();
        if(successor != null) {
            successor.handle();
        }
    }

    protected abstract void doHandle() ;
}
