package com.hyf.backend.common.config;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
public class FeignUidInterceptorConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String uid = ContextHolder.getCurrentContext().get(Constant.X_UID);
        template.header(Constant.X_UID, uid);
    }
}
