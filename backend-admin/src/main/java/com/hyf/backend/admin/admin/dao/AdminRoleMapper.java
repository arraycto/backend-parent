package com.hyf.backend.admin.admin.dao;

import com.hyf.backend.admin.admin.dataobject.AdminRoleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AdminRoleMapper {
    List<AdminRoleDO> queryByIds(@Param("ids") List<Integer> ids);

    @Select("select * from admin_role")
    List<AdminRoleDO> selectAllRoleList();
}
