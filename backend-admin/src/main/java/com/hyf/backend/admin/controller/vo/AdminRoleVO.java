package com.hyf.backend.admin.controller.vo;

import com.hyf.backend.admin.admin.bo.AdminRoleBO;
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
public class AdminRoleVO {
    private Integer id;
    private String name;
    private String desc;
    private Boolean enabled;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;

    public AdminRoleVO(AdminRoleBO adminRoleBO) {
        BeanUtils.copyProperties(adminRoleBO, this);
    }
}
