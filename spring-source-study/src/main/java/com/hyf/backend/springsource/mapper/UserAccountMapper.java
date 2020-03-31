package com.hyf.backend.springsource.mapper;

import com.hyf.backend.springsource.service.UserAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserAccountMapper {
    @Update("update user_account set balance = #{balance} where user_id = #{userId}")
    int updateDecrAccount(@Param("userId") Integer userId, @Param("balance") Integer balance);
    @Update("update user_account set balance = #{balance} where user_id = #{userId}")
    int updateIncrAccount(@Param("userId") Integer userId, @Param("balance") Integer balance);

    @Update("update user_account set balance = #{balance} , version = version + 1 where user_id = #{userId} and version = #{version}")
    int updateDecrAccountCAS(@Param("userId") Integer userId, @Param("balance") Integer balance, @Param("version") Integer version);
    @Update("update user_account set balance = #{balance}, version = version + 1 where user_id = #{userId} and version = #{version}")
    int updateIncrAccountCAS(@Param("userId") Integer userId, @Param("balance") Integer balance, @Param("version") Integer version);


    @Select("select * from user_account where user_id = #{userId}")
    UserAccount selectByUserId(@Param("userId") Integer userId);
}
