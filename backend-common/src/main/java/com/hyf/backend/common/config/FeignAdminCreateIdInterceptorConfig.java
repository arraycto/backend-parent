package com.hyf.backend.common.config;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class FeignAdminCreateIdInterceptorConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String adminUid = ContextHolder.getCurrentContext().get(Constant.X_ADMIN_UID);
        template.header(Constant.X_ADMIN_UID, adminUid);
    }
}
