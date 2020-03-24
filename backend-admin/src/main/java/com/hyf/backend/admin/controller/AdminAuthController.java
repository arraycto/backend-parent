package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.admin.dataobject.AdminPermissionDO;
import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.admin.service.AdminPermissionService;
import com.hyf.backend.admin.admin.service.AdminRoleService;
import com.hyf.backend.admin.admin.service.AdminUserService;
import com.hyf.backend.admin.controller.dto.AdminUserLoginDTO;
import com.hyf.backend.admin.controller.vo.AdminLoginVO;
import com.hyf.backend.admin.controller.vo.AdminUserInfoVO;
import com.hyf.backend.admin.util.AdminResponseCode;
import com.hyf.backend.admin.util.IpUtil;
import com.hyf.backend.admin.util.Permission;
import com.hyf.backend.admin.util.PermissionUtil;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/auth")
@Validated
public class AdminAuthController {

    private final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
    @Autowired
    private AdminUserService adminService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminPermissionService adminPermissionService;

    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    @PostMapping("/login")
    public ResponseVO<AdminLoginVO> login(@RequestBody AdminUserLoginDTO adminUserLoginDTO, HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(adminUserLoginDTO.getUsername(), adminUserLoginDTO.getPassword()));
        } catch (UnknownAccountException uae) {
//            logHelper.logAuthFail("登录", "用户帐号或密码不正确");
            return ResponseVO.error(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
//            logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseVO.error(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
//            logHelper.logAuthFail("登录", "认证失败");
            return ResponseVO.error(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "认证失败");
        }

        currentUser = SecurityUtils.getSubject();
        AdminUserDO admin = (AdminUserDO) currentUser.getPrincipal();
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        admin.setUpdateTime(new Date());
        adminService.updateById(admin);

        return ResponseVO.ok(new AdminLoginVO().
                setAvatar(admin.getAvatar()).
                setNickName(admin.getUsername()).
                setToken((String) currentUser.getSession().getId()));
    }

    @RequiresAuthentication
    @PostMapping("/logout")
    public ResponseVO logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseVO.ok();
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public ResponseVO<AdminUserInfoVO> info() {
        Subject subject = SecurityUtils.getSubject();
        AdminUserDO adminUserDO = (AdminUserDO) subject.getPrincipal();
        Integer[] roleIds = adminUserDO.getRoleIds();
        List<AdminRoleDO> adminRoleDOList = adminRoleService.queryByIds(Arrays.asList(roleIds));
        Set<String> roleNames = adminRoleDOList.stream().map(AdminRoleDO::getName).collect(Collectors.toSet());
        List<AdminPermissionDO> adminPermissionDOS = adminPermissionService.queryByRoleIds(Arrays.asList(roleIds));
        List<String> permissionList = adminPermissionDOS.stream().map(AdminPermissionDO::getPermission).collect(Collectors.toList());
        return ResponseVO.ok(new AdminUserInfoVO().setAvatar(adminUserDO.getAvatar())
                .setUsername(adminUserDO.getUsername())
                .setRoles(roleNames).setPerms(toApi(permissionList)));
    }

    private Set<String> toApi(List<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "com.hyf.backend.admin.controller";
            List<Permission> systemPermissions = PermissionUtil.listSysPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                systemPermissionsMap.put(perm, api);
            }
        }

        Set<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
                //                return systemPermissionsMap.values();

            }
        }
        return apis;
    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseVO.error(-1, "请登录");
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseVO.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseVO.error(-1, "没有授权");
    }

}
