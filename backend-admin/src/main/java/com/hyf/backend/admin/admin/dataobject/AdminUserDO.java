package com.hyf.backend.admin.admin.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class AdminUserDO {
    private Integer id;
    private String username;
    private String password;
    private String lastLoginIp;
    private Date lastLoginTime;
    private String avatar;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;
    private Integer[] roleIds;


}
