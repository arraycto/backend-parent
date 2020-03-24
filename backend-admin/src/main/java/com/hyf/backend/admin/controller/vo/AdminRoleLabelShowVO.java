package com.hyf.backend.admin.controller.vo;

import com.hyf.backend.admin.admin.bo.AdminRoleBO;
import lombok.Data;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class AdminRoleLabelShowVO {
    private Integer id;
    private String label;

    public AdminRoleLabelShowVO(AdminRoleBO adminRoleBO) {
        this.id = adminRoleBO.getId();
        this.label = adminRoleBO.getName();
    }
}
