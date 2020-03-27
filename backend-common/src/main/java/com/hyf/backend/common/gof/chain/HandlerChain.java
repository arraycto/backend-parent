package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/3/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerChain {
    private Handler head;
    private Handler tail;

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);
        if(head == null) {
            head = handler;
            tail = handler;
        }
        tail.setSuccessor(handler);
        tail = handler;
    }
    public void handle() {
        if(head != null) {
            head.handle();
        }
    }

    public static void main(String[] args) {
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(new HandlerA());
        handlerChain.addHandler(new HandlerB());
        handlerChain.handle();
    }

}
