package com.hyf.backend.admin.admin.dao;

import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: 管理员列表分页排序
 */
public interface AdminUserMapper {
    List<AdminUserDO> selectListByPage(@Param("username") String username,
                                       @Param("sort") String sort,
                                       @Param("order") String order,
                                       @Param("offset") Integer offset,
                                       @Param("limit") Integer limit);

    Long selectCountByPage(@Param("username") String username);

    int updateByIdSelective(AdminUserDO admin);

    List<AdminUserDO> selectAdminByUserName(@Param("username") String username);
}
