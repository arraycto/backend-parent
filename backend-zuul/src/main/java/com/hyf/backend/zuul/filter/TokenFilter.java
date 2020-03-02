package com.hyf.backend.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 校验请求中传递的token
 */
@Component
@Slf4j
public class TokenFilter extends AbstractPreFilter{
    @Override
    protected Object doRun() {
        //在父类的run方法中已经获得了RequestContext
        log.info("Token Filter Thrad: {}", Thread.currentThread().getName());
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        log.info("Authorization: {}", authorization);
        log.info(String.format("%s request to  %s", request.getMethod(), request.getRequestURL().toString()));
        String token = request.getParameter("token");
        if(Objects.isNull(token)) {
            log.error("error: token is empty");
            return fail(401, "token is empty");
        }
        //做token校验的逻辑

        //......
//        requestContext.addZuulRequestHeader("Authorization", authorization);

        //继续向下执行过滤器
        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
