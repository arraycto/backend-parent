package com.hyf.backend.common.gof.chain;

/**
 * @Author: Elvis on 2020/4/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HandlerChain {
    private Handler head;
    private Handler tail;

    public void addHandler(Handler handler) {
        handler.setNextHandler(null);
        if (head == null) {
            head = handler;
            tail = handler;
        }
        tail.setNextHandler(handler);
        tail = handler;
    }

    public void handle(String msg) {
        if(head != null) {
            head.handle(msg);
        }
    }


    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        chain.addHandler(new HandlerC());
        chain.handle("haha");
    }
}
