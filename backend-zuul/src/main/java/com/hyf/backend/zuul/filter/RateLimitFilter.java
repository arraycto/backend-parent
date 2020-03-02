package com.hyf.backend.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 根据IP限流
 */
@Component
@Slf4j
public class RateLimitFilter extends AbstractPreFilter {
    //每秒获取两个令牌
    private RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    protected Object doRun() {
        HttpServletRequest request = requestContext.getRequest();
        if (rateLimiter.tryAcquire()) {
            log.info("限流通过");
            return success();
        } else {
            log.error("限流了...{}", request.getRequestURI());
            return fail(402, "限流了");
        }
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
