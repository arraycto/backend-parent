package com.hyf.backend.coupon.template.feign;

import com.hyf.backend.coupon.template.fallback.UserServiceFallback;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elvis on 2020/3/2
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@FeignClient(value = "user-service", fallbackFactory = UserServiceFallback.class, path = "/user")
public interface UserServiceApiClient {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    ResponseVO<String> hello(@RequestParam("hello") String hello);
}
