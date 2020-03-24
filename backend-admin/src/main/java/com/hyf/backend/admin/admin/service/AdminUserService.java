package com.hyf.backend.admin.admin.service;

import com.hyf.backend.admin.admin.bo.AdminUserPageBO;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.controller.dto.AdminUserQueryPageDTO;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AdminUserService {

    AdminUserPageBO getAdminUserByPage(AdminUserQueryPageDTO adminUserQueryPageDTO);

    void updateById(AdminUserDO admin);

    AdminUserDO findAdminByUserName(String username);
}
