package com.hyf.backend.admin.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class AdminUserUpdateDTO {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private List<Integer> roleIds;
}
