package com.hyf.backend.user.service;

import com.hyf.backend.utils.exception.BizException;

/**
 * @Author: Elvis on 2020/2/14
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserServiceApi {
    String checkUserLogin(String username,String password) throws BizException;
}
