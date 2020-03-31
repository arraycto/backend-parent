package com.hyf.backend.springsource;

import com.hyf.backend.springsource.dto.TransferDTO;
import com.hyf.backend.springsource.service.UserAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringSourceAppTests {

    @Autowired
    private UserAccountService userAccountService;

    @Test
    public void test() throws InterruptedException {
        TransferDTO dto = new TransferDTO();
        dto.setFromUserId(1);
        dto.setToUserId(10);
        dto.setAmount(1);
        int clientTotal = 100;
        // 同时并发执行的线程数
        int threadTotal = 10;
        int count = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        //信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    //允许通行，否则线程阻塞等待，直到获取到许可。
                    semaphore.acquire();
                    userAccountService.transfer(dto);
                    //释放许可
                    semaphore.release();
                } catch (Exception e) {
                    //log.error("exception", e);
                    e.printStackTrace();

                }
                //闭锁减一
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();//线程阻塞，直到闭锁值为0时，阻塞才释放，继续往下执行
        executorService.shutdown();

    }
}
