package com.hyf.backend.common.advice;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandleAdvice {

    @ExceptionHandler(BizException.class)
    public ResponseVO<?> handlerBizException(HttpServletRequest request, BizException e) {
        log.error("业务异常: code: {}, msg: {}", e.getCode(), e.getMessage());
        return ResponseVO.serviceException(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error("参数校验异常{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String errMsg = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return ResponseVO.error(-3, errMsg);
    }
}
