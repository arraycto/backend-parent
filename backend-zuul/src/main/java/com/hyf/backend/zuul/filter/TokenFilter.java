package com.hyf.backend.zuul.filter;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 校验请求中传递的token
 */
@Component
@Slf4j
public class TokenFilter extends AbstractPreFilter {
    @Override
    protected Object doRun() {
        //在父类的run方法中已经获得了RequestContext
        HttpServletRequest request = requestContext.getRequest();
        log.info("URI: {}", request.getRequestURI());
        if (request.getRequestURI().contains("/user/login")) {
            return success();
        }

        String authorization = request.getHeader("X-Litemall-Token");
        log.info("Authorization: {}", authorization);
        log.info(String.format("%s request to  %s", request.getMethod(), request.getRequestURL().toString()));
        if (StringUtils.isEmpty(authorization)) {
            log.error("toke is empty");
//            return fail(401, -1, "token is empty");
            return success();
        }
        JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(authorization);
        if (jwtResult.isStatus()) {
            log.info("token合法, token: {}, uid: {} ", authorization, jwtResult.getUid());
            requestContext.addZuulRequestHeader(Constant.X_UID, jwtResult.getUid());
            requestContext.addZuulRequestHeader(Constant.X_TOKEN, authorization);
            return success();
        } else {
            log.error("token校验失败: {}", jwtResult.getMsg());
//            throw new BizException(-1, "token校验失败");
            if(jwtResult.getCode() != 401) {
                return fail(HttpStatus.UNAUTHORIZED.value(), jwtResult.getCode(), jwtResult.getMsg());
            }
            return success();

        }

    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
