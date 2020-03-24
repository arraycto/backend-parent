package com.hyf.backend.admin.admin.dao;

import com.hyf.backend.admin.admin.dataobject.AdminPermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AdminPermissionMapper {
    List<AdminPermissionDO> queryByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
