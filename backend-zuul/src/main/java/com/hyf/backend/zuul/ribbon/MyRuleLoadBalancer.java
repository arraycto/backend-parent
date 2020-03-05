//package com.hyf.backend.zuul.ribbon;
//
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Author: Elvis on 2020/2/29
// * @Email: yfelvis@gmail.com
// * @Desc: TODO
// */
//@Slf4j
//@Component
//public class MyRuleLoadBalancer extends AbstractLoadBalancerRule {
//    @Override
//    public void initWithNiwsConfig(IClientConfig clientConfig) {
//
//    }
//
//    @Override
//    public Server choose(Object key) {
//        return choose(getLoadBalancer(), key);
//    }
//
//    private Server choose(ILoadBalancer lb, Object key) {
//        log.info("Current Custom LoadBalancer thread: {}", Thread.currentThread().getName());
//        List<Server> reachableServers = lb.getReachableServers();
//        return reachableServers.get(0);
//    }
//}
