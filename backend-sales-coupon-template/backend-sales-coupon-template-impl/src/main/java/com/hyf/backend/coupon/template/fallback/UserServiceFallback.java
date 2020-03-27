package com.hyf.backend.coupon.template.fallback;

import com.hyf.backend.coupon.template.feign.UserServiceApiClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/3/2
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class UserServiceFallback implements FallbackFactory<UserServiceApiClient> {
    @Override
    public UserServiceApiClient create(Throwable throwable) {
        log.error("调用用户服务出异常....", throwable);
        return new UserServiceApiClient() {
            @Override
            public ResponseVO<String> hello(String hello) {
                return ResponseVO.error("hystrix fallback");
            }
        };
    }
}
