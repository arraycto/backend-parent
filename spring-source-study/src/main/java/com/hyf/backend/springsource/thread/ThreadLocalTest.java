package com.hyf.backend.springsource.thread;

/**
 * @Author: Elvis on 2020/3/30
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ThreadLocalTest {
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            threadLocal.set("hyf");
            new Thread(()->{
                new Test1().call();;
            }).start();

        }).start();
    }

    static class Test1 {
        public void call() {
            System.out.println(Thread.currentThread().getName() + " "+threadLocal.get());
        }
    }
}
