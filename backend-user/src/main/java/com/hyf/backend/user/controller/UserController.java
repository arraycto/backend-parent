package com.hyf.backend.user.controller;

import com.google.common.collect.Maps;
import com.hyf.backend.user.controller.vo.LoginVO;
import com.hyf.backend.user.service.UserServiceApiImpl;
import com.hyf.backend.utils.JwtTokenUtil;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Elvis on 2020/2/13
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceApiImpl userServiceApi;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVO login(@RequestBody LoginVO loginVO) throws BizException {
        loginVO.checkParam();
        String userId = userServiceApi.checkUserLogin(loginVO.getUsername(), loginVO.getPassword());
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        String randomKey = jwtTokenUtil.getRandomKey();
        String token = jwtTokenUtil.generateToken(userId, randomKey);

        Map<String, String> result = Maps.newHashMap();
        result.put("randomKey", randomKey);
        result.put("token", token);

        return ResponseVO.ok(result);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseVO<String> hello() throws InterruptedException {
        log.info("请求test...");
        Thread.sleep(3000);
        return ResponseVO.ok("hello");
    }
}
