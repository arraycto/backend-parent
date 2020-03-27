package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.admin.service.AdminUserService;
import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.controller.dto.AdminUserCreateDTO;
import com.hyf.backend.admin.controller.dto.AdminUserQueryPageDTO;
import com.hyf.backend.admin.controller.dto.AdminUserUpdateDTO;
import com.hyf.backend.admin.controller.vo.AdminUserPageVO;
import com.hyf.backend.admin.controller.vo.AdminUserVO;
import com.hyf.backend.common.domain.IntegerIdReqDto;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/list")
    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    public ResponseVO<AdminUserPageVO> listAdminUser(@RequestBody AdminUserQueryPageDTO adminUserQueryPageDTO) {
        return ResponseVO.ok(new AdminUserPageVO(adminUserService.getAdminUserByPage(adminUserQueryPageDTO)));
    }

    @PostMapping("/create")
    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "创建")
    public ResponseVO<AdminUserVO> createAdminUser(@RequestBody AdminUserCreateDTO adminUserCreateDTO) {
        return ResponseVO.ok(new AdminUserVO(adminUserService.createAdminUser(adminUserCreateDTO)));
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:admin:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "修改")
    public ResponseVO<AdminUserVO> updateAdminUser(@RequestBody AdminUserUpdateDTO adminUserUpdateDTO) {
        return ResponseVO.ok(new AdminUserVO(adminUserService.updateAdminUser(adminUserUpdateDTO)));
    }

    @PostMapping("/delete")
    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "删除")
    public ResponseVO<AdminUserVO> deleteAdminUser(@RequestBody IntegerIdReqDto integerIdReqDto) {
        return ResponseVO.ok(new AdminUserVO(adminUserService.deleteAdminUser(integerIdReqDto.getId())));
    }
}
