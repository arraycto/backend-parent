package com.hyf.backend.zuul.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: 在过滤器中存储客户端发起请求的时间戳
 */
@Component
@Slf4j
public class PreAccessLogFilter extends AbstractPreFilter {
    @Override
    protected Object doRun() {
        //记录请求时间戳
        requestContext.set("startTime", System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return -1;
    }
}
