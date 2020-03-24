package com.hyf.backend.admin.controller.vo;

import com.hyf.backend.admin.admin.bo.AdminUserPageBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class AdminUserPageVO implements Serializable {
    private List<AdminUserVO> list;
    private Long total;

    public AdminUserPageVO(AdminUserPageBO adminUserPageBO) {
        this.list = adminUserPageBO.getList().stream().map(AdminUserVO::new).collect(Collectors.toList());
        this.total = adminUserPageBO.getTotal();
    }
}
