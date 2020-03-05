package com.hyf.backend.zuul.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
//@Component
public class PostAccessLogFilter extends AbstractPostFilter {
    @Override
    protected Object doRun() {
        Long startTime = (Long) requestContext.get("startTime");
        Long diff = System.currentTimeMillis() - startTime;
        log.info("处理耗时: ip:{}, duration:{} uri:{}", requestContext.getRequest().getRemoteAddr(), diff, requestContext.getRequest().getRequestURI());
        return success();
    }

    /**
     * 在SendResponseFilter之前执行
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
