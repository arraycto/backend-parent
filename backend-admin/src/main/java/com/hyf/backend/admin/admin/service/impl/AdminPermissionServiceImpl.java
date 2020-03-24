package com.hyf.backend.admin.admin.service.impl;

import com.hyf.backend.admin.admin.dao.AdminPermissionMapper;
import com.hyf.backend.admin.admin.dataobject.AdminPermissionDO;
import com.hyf.backend.admin.admin.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    @Override
    public List<AdminPermissionDO> queryByRoleIds(List<Integer> roleIds) {
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return adminPermissionMapper.queryByRoleIds(roleIds);
    }
}
