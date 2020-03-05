package com.hyf.backend.user.service;

import com.hyf.backend.user.controller.vo.UserAuthVO;
import com.hyf.backend.user.dto.UserAuthDTO;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserAuthService {

    UserAuthVO authUser(UserAuthDTO authDTO);
}
