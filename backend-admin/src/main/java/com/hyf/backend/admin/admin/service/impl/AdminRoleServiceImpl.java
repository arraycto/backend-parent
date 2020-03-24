package com.hyf.backend.admin.admin.service.impl;

import com.hyf.backend.admin.admin.bo.AdminRoleBO;
import com.hyf.backend.admin.admin.dao.AdminRoleMapper;
import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;
import com.hyf.backend.admin.admin.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public List<AdminRoleDO> queryByIds(List<Integer> roleIds) {
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return adminRoleMapper.queryByIds(roleIds);
    }

    @Override
    public List<AdminRoleBO> getAllRoleList() {
        return adminRoleMapper.selectAllRoleList().stream().map(AdminRoleBO::new).collect(Collectors.toList());
    }
}
