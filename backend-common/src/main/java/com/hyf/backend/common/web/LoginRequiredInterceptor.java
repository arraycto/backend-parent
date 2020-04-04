package com.hyf.backend.common.web;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("handler class: {}", handler.getClass());
//        String uid = request.getHeader(Constant.X_UID);
//        ContextHolder.getCurrentContext().add(Constant.X_UID, uid);
        return true;
    }
}
