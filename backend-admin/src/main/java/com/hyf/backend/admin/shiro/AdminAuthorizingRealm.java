package com.hyf.backend.admin.shiro;


import com.google.common.collect.Sets;
import com.hyf.backend.admin.admin.dataobject.AdminPermissionDO;
import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.admin.service.AdminPermissionService;
import com.hyf.backend.admin.admin.service.AdminRoleService;
import com.hyf.backend.admin.admin.service.AdminUserService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminRoleService roleService;
    @Autowired
    private AdminPermissionService permissionService;

    //做授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        //就是创建SimpleAuthenticationInfo对象的第一个参数
        AdminUserDO admin = (AdminUserDO) getAvailablePrincipal(principals);
        Integer[] roleIds = admin.getRoleIds();
//        Set<String> roles = roleService.queryByIds(Arrays.asList(roleIds));
        List<AdminRoleDO> adminRoleDOList = roleService.queryByIds(Arrays.asList(roleIds));
        List<String> roles = adminRoleDOList.stream().map(AdminRoleDO::getName).collect(Collectors.toList());
//        Set<String> permissions = permissionService.queryByRoleIds(Arrays.asList(roleIds));
        List<AdminPermissionDO> adminPermissionDOList = permissionService.queryByRoleIds(Arrays.asList(roleIds));
        List<String> permissions = adminPermissionDOList.stream().map(AdminPermissionDO::getPermission).collect(Collectors.toList());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(Sets.newHashSet(roles));
        info.setStringPermissions(Sets.newHashSet(permissions));
        return info;
    }

    /**
     * 先根据用户名查询出admin用户
     *
     * @param token 封装了用户名和密码的UsernamePasswordToken的类
     * @return 返回认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AccountException("密码不能为空");
        }

        AdminUserDO adminUserDO = adminUserService.findAdminByUserName(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, adminUserDO.getPassword())) {
            throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
        }

        return new SimpleAuthenticationInfo(adminUserDO, password, getName());
    }

}
