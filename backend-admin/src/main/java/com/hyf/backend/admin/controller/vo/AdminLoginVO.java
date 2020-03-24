package com.hyf.backend.admin.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: 管理员用户登录后VO
 */
@Data
@Accessors(chain = true)
public class AdminLoginVO {
    private String token;
    private String nickName;
    private String avatar;
}
