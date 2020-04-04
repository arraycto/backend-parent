//package com.hyf.backend.coupon.template;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
///**
// * @Author: Elvis on 2020/3/31
// * @Email: yfelvis@gmail.com
// * @Desc: TODO
// */
//@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-topic-consumer-group")
//@Slf4j
//@Component
//public class TestRocketMQListener implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String message) {
//        log.info("收到消息了....message: {}", message);
//    }
//}
