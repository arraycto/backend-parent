package com.hyf.backend.zuul.filter;

import com.hyf.backend.utils.exception.BizException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.ZuulFilterInitializer;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
@Component
public class AuthErrorFilter extends ZuulFilter {
    private String errorPath = "/error";
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable throwable = ctx.getThrowable();
            BizException oauthException;
            // throwable 为 ZuulException 类型，throw.getCause() 为 BizException 类型
            if (throwable.getCause() instanceof BizException) {
                oauthException = (BizException) throwable.getCause();
                log.error(oauthException.getMessage(), oauthException);
                HttpServletRequest request = ctx.getRequest();
                RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
                if (dispatcher != null) {
                    ctx.set("sendErrorFilter.ran", true);   // 这样就不会执行 SendErrorFilter 类中的 run 方法
                    if (!ctx.getResponse().isCommitted()) {
                        ctx.setResponseStatusCode(oauthException.getCode());
                        dispatcher.forward(request, ctx.getResponse());     // 执行自定义 /error 方法
                    }
                }
            }
        } catch (Exception var1) {
            ReflectionUtils.rethrowRuntimeException(var1);
        }

        return null;
    }
}
