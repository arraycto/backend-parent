package com.hyf.backend.common.filter;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HttpHeaderAdminCreateIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String adminUid = request.getHeader(Constant.X_ADMIN_UID);
        ContextHolder.getCurrentContext().add(Constant.X_ADMIN_UID, adminUid);
        filterChain.doFilter(request, response);
    }
}
