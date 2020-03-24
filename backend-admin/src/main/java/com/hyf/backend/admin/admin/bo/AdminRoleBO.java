package com.hyf.backend.admin.admin.bo;

import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class AdminRoleBO {
    private Integer id;
    private String name;
    private String desc;
    private Boolean enabled;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;

    public AdminRoleBO(AdminRoleDO adminRoleDO) {
        BeanUtils.copyProperties(adminRoleDO, this);
    }
}
