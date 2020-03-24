package com.hyf.backend.admin.admin.service.impl;

import com.hyf.backend.admin.admin.bo.AdminUserBO;
import com.hyf.backend.admin.admin.bo.AdminUserPageBO;
import com.hyf.backend.admin.admin.dao.AdminUserMapper;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.controller.dto.AdminUserQueryPageDTO;
import com.hyf.backend.admin.admin.service.AdminUserService;
import com.hyf.backend.utils.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUserPageBO getAdminUserByPage(AdminUserQueryPageDTO adminUserQueryPageDTO) {
        int offset = (adminUserQueryPageDTO.getPage() - 1) * adminUserQueryPageDTO.getLimit();
        List<AdminUserDO> adminUserDOList = adminUserMapper.selectListByPage(adminUserQueryPageDTO.getUsername(), adminUserQueryPageDTO.getSort(),
                adminUserQueryPageDTO.getOrder(), offset, adminUserQueryPageDTO.getLimit());
        Long total = adminUserMapper.selectCountByPage(adminUserQueryPageDTO.getUsername());

        AdminUserPageBO adminUserPageBO = new AdminUserPageBO();
        adminUserPageBO.setList(adminUserDOList.stream().map(AdminUserBO::new).collect(Collectors.toList()));
        adminUserPageBO.setTotal(total);

        return adminUserPageBO;
    }

    @Override
    public void updateById(AdminUserDO admin) {
        adminUserMapper.updateByIdSelective(admin);
    }

    @Override
    public AdminUserDO findAdminByUserName(String username) {
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectAdminByUserName(username);
        if(adminUserDOS.size() > 1) {
            throw new BizException("用一个账户名称存在两个用户");
        }
        if(adminUserDOS.size() == 0) {
            throw new BizException("找不到用户" + username + "信息");
        }
        return adminUserDOS.get(0);
    }
}
