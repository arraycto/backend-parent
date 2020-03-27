package com.hyf.backend.admin.admin.service.impl;

import com.hyf.backend.admin.admin.bo.AdminUserBO;
import com.hyf.backend.admin.admin.bo.AdminUserPageBO;
import com.hyf.backend.admin.admin.dao.AdminUserMapper;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.admin.service.AdminUserService;
import com.hyf.backend.admin.controller.dto.AdminUserCreateDTO;
import com.hyf.backend.admin.controller.dto.AdminUserQueryPageDTO;
import com.hyf.backend.admin.controller.dto.AdminUserUpdateDTO;
import com.hyf.backend.utils.exception.BizException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        if (adminUserDOS.size() > 1) {
            throw new BizException("用一个账户名称存在两个用户");
        }
        if (adminUserDOS.size() == 0) {
            throw new BizException("找不到用户" + username + "信息");
        }
        return adminUserDOS.get(0);
    }

    @Override
    public AdminUserBO createAdminUser(AdminUserCreateDTO adminUserCreateDTO) {
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectAdminByUserName(adminUserCreateDTO.getUsername());
        if (adminUserDOS.size() > 0) {
            throw new BizException("已经存在同名的管理员");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AdminUserDO adminUserDO = new AdminUserDO()
                .setAddTime(new Date())
                .setAvatar(adminUserCreateDTO.getAvatar())
                .setDeleted(false)
                .setPassword(encoder.encode(adminUserCreateDTO.getPassword()))
                .setRoleIds(adminUserCreateDTO.getRoleIds().toArray(new Integer[0]))
                .setUpdateTime(new Date())
                .setUsername(adminUserCreateDTO.getUsername());
        adminUserMapper.insert(adminUserDO);
        return new AdminUserBO(adminUserDO);
    }

    @Override
    public AdminUserBO updateAdminUser(AdminUserUpdateDTO adminUserUpdateDTO) {
        AdminUserDO adminUserDO = adminUserMapper.selectById(adminUserUpdateDTO.getId());
        if (null == adminUserDO) {
            throw new BizException("该用户不存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AdminUserDO toUpdate = new AdminUserDO();
        BeanUtils.copyProperties(adminUserUpdateDTO, toUpdate);
        toUpdate.setRoleIds(adminUserUpdateDTO.getRoleIds().toArray(new Integer[0]));
        toUpdate.setPassword(encoder.encode(adminUserUpdateDTO.getPassword()));
        adminUserMapper.updateByIdSelective(toUpdate);
        return new AdminUserBO(adminUserMapper.selectById(adminUserUpdateDTO.getId()));
    }

    @Override
    public AdminUserBO deleteAdminUser(Integer id) {
        Subject subject = SecurityUtils.getSubject();
        AdminUserDO principal = (AdminUserDO) subject.getPrincipal();
        if (id.equals(principal.getId())) {
            throw new BizException("超级管理员不能删除自己");
        }
        adminUserMapper.deleteById(id);
        return new AdminUserBO(adminUserMapper.selectById(id));
    }
}
