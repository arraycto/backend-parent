package com.hyf.backend.utils.common.vo;

import com.hyf.backend.utils.exception.BizException;

/**
 * @Author: Elvis on 2020/2/13
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class BaseRequestVO {

    /**
     * 公共的参数验证方法
     */
    public abstract void checkParam() throws BizException;
}
