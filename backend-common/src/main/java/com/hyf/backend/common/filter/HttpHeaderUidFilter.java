package com.hyf.backend.common.filter;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接收服务传递过来的用户信息
 */
public class HttpHeaderUidFilter implements Filter {
    public static ThreadLocal<Long> loginUserThreadLocal = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uid = httpRequest.getHeader(Constant.X_UID);
        ContextHolder.getCurrentContext().add(Constant.X_UID, uid);
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }
}