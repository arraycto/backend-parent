package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.admin.bo.AdminRoleBO;
import com.hyf.backend.admin.admin.service.AdminRoleService;
import com.hyf.backend.admin.controller.vo.AdminRoleLabelShowVO;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@Validated
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @GetMapping("/role-options")
    public ResponseVO<ListVO<AdminRoleLabelShowVO>> roleOptionsList() {
        List<AdminRoleBO> allRoleList = adminRoleService.getAllRoleList();
        return ResponseVO.ok(new ListVO<>(allRoleList.stream().map(AdminRoleLabelShowVO::new).collect(Collectors.toList())));
    }
}
