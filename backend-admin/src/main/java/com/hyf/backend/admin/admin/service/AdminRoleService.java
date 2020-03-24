package com.hyf.backend.admin.admin.service;

import com.hyf.backend.admin.admin.bo.AdminRoleBO;
import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AdminRoleService {

    List<AdminRoleDO> queryByIds(List<Integer> roleIds);

    List<AdminRoleBO> getAllRoleList();

}
