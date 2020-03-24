package com.hyf.backend.user.controller;

import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.user.controller.vo.UserAuthReq;
import com.hyf.backend.user.controller.vo.UserAuthVO;
import com.hyf.backend.user.service.UserAuthService;
import com.hyf.backend.user.service.UserServiceImpl;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private UserServiceImpl userService;
    @Autowired
    private UserAuthService userAuthService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVO<UserAuthVO> login(@Valid @RequestBody UserAuthReq loginVO) throws BizException {
        return ResponseVO.ok(userAuthService.authUser(loginVO.toDTO()));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseVO<String> hello(@RequestParam("hello") String hello, HttpServletRequest request) throws InterruptedException {
        String token = request.getHeader("token");
//        log.info("user token: {}", token);
//        log.info("uid: {}", ContextHolder.getCurrentContext().get("uid"));
//        log.info("请求test...");
//        Thread.sleep(10000);
        log.info("hello: {}", hello);

//        return ResponseVO.ok("hello");
        throw new BizException("用户服务出现异常");
    }
}
