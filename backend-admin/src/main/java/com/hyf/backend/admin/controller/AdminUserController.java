package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.controller.dto.AdminUserQueryPageDTO;
import com.hyf.backend.admin.admin.service.AdminUserService;
import com.hyf.backend.admin.controller.vo.AdminUserPageVO;
import com.hyf.backend.admin.controller.vo.AdminUserVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/admin")
@Validated
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/list")
    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    public ResponseVO<AdminUserPageVO> listAdminUser(AdminUserQueryPageDTO adminUserQueryPageDTO) {
        return ResponseVO.ok(new AdminUserPageVO(adminUserService.getAdminUserByPage(adminUserQueryPageDTO)));
    }

    @PostMapping("/create")
    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "创建")
    public ResponseVO<AdminUserVO> createAdminUser() {
        return ResponseVO.ok(new AdminUserVO());
    }



}
