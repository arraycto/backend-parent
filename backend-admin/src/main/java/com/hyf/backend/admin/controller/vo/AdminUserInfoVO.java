package com.hyf.backend.admin.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class AdminUserInfoVO {
    private String username;
    private String avatar;
    private Set<String> roles;
    private Set<String> perms;
}
