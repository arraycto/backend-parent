package com.hyf.backend.coupon.template.controller;

import com.hyf.backend.coupon.template.feign.UserServiceApiClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@Slf4j
public class CouponTemplateController {
    @Autowired
    private UserServiceApiClient userServiceApiClient;

    @Value("${test.name:hahaha}")
    private String name;

    @GetMapping("/get")
    public String get(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
            log.info("headerName: {}, headerValue: {}", s, header);
        }
        return "Hello Docker";
    }

    @GetMapping("/getUser")
    public ResponseVO<String> getUser(HttpServletRequest request) {
        log.info("request remoteAdder: {}", request.getRemoteAddr());
        ResponseVO<String> hello = userServiceApiClient.hello("haahhahahaha");
        if (hello.isOk()) {
            return hello;
        } else {
            throw new BizException(-1, hello.getMsg());
        }
    }

    @GetMapping("/test")
    public String test(String code) {
        log.info("收到的code参数: {}", code);
        return name;
    }

}
