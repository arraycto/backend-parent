package com.hyf.backend.utils.exception;

import lombok.Data;

/**
 * @Author: Elvis on 2020/2/14
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class BizException extends RuntimeException {
    private Integer code;
    private String message;

    public BizException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
