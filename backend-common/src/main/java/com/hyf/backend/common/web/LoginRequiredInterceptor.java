package com.hyf.backend.common.web;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.utils.JWTUtils;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
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
//        String uid = request.getHeader(Constant.X_U`ID);
//        ContextHolder.getCurrentContext().add(Constant.X_UID, uid);
        log.info("uid: {}", request.getHeader(Constant.X_UID));
        log.info("token: {}", request.getHeader(Constant.X_TOKEN));
        log.info("x-token: {}", request.getHeader("X-Litemall-Token"));
        String token = request.getHeader("X-Litemall-Token");
        if(StringUtils.isNotEmpty(token)) {
            JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(request.getHeader("X-Litemall-Token"));
            if (jwtResult.isStatus()) {
//                requestContext.addZuulRequestHeader(Constant.X_UID, jwtResult.getUid());
//                requestContext.addZuulRequestHeader(Constant.X_TOKEN, authorization);
                ContextHolder.getCurrentContext().add(Constant.X_UID, jwtResult.getUid());
//                return success();
            } else {
                log.error("token校验失败: {}", jwtResult.getMsg());
                if(jwtResult.getCode() != 401) {
                    throw new BizException("无效的token信息");
                }
//            throw new BizException(-1, "token校验失败");
//                return fail(HttpStatus.UNAUTHORIZED.value(), jwtResult.getCode(), jwtResult.getMsg());
            }
        }

        return true;
    }
}
