package com.hyf.backend.admin.shiro.filter;

import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class AdminFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            ContextHolder.getCurrentContext().add(Constant.X_ADMIN_UID, String.valueOf(((AdminUserDO) subject.getPrincipal()).getId()));
        }
        return true;
    }
}
