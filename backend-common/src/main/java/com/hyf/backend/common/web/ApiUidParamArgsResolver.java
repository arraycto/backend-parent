package com.hyf.backend.common.web;

import com.hyf.backend.common.annotation.CurrentUser;
import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Optional;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ApiUidParamArgsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getMethodAnnotation(CurrentUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String uid = ContextHolder.getCurrentContext().get(Constant.X_UID);
        return Optional.ofNullable(uid).orElseThrow(() -> new MissingServletRequestPartException(uid + " current user"));
    }
}
