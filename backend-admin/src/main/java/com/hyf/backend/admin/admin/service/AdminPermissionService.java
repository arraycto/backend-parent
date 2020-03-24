package com.hyf.backend.admin.admin.service;

import com.hyf.backend.admin.admin.dataobject.AdminPermissionDO;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AdminPermissionService {
    List<AdminPermissionDO> queryByRoleIds(List<Integer> roleIds);
}
