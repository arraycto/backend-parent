//package com.hyf.backend.common.advice;
//
//import com.hyf.backend.common.annotation.IgnoreCommonResAdvice;
//import com.hyf.backend.utils.common.vo.BaseResponseVO;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @Author: Elvis on 2020/2/25
// * @Email: yfelvis@gmail.com
// * @Desc: TODO
// */
//@RestControllerAdvice
//public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        //类标注了注解
//        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreCommonResAdvice.class)) {
//            return false;
//        }
//        //方法上标注了忽略统一返回处理
//        if (methodParameter.getMethod().isAnnotationPresent(IgnoreCommonResAdvice.class)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object o,
//                                  MethodParameter methodParameter,
//                                  MediaType mediaType,
//                                  Class<? extends HttpMessageConverter<?>> aClass,
//                                  ServerHttpRequest serverHttpRequest,
//                                  ServerHttpResponse serverHttpResponse) {
//        //o就是返回对象
//        BaseResponseVO<Object> responseVO = new BaseResponseVO<>();
//        responseVO.setCode(0);
//        responseVO.setMsg("success");
//        if (o == null) {
//            return responseVO;
//        } else if (o instanceof BaseResponseVO) {
//            responseVO = (BaseResponseVO) o;
//        } else {
//            responseVO.setData(o);
//        }
//        return responseVO;
//    }
//}
