package com.hyf.backend.admin.admin.bo;

import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserBO {
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

    public AdminUserBO(AdminUserDO adminUserDO) {
        BeanUtils.copyProperties(adminUserDO, this);
    }
}
