package com.hyf.backend.common.advice;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandleAdvice {

    @ExceptionHandler(BizException.class)
    public ResponseVO<?> handlerBizException(HttpServletRequest request, BizException e) {
        log.error("业务异常: code: {}, msg: {}", e.getCode(), e.getMessage());
        return ResponseVO.serviceException(e);
    }
}
