package com.hyf.backend.admin.storage.dao;

import com.hyf.backend.admin.storage.dataobject.StorageDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface StorageMapper {
    @Select("select * from admin_storage where `key` = #{key}")
    StorageDO selectByKey(@Param("key") String key);

    @Insert("insert into admin_storage(`key`, name, type, size, url, add_time, update_time, deleted) values(#{key}, #{name}, #{type}, #{size}, #{url}, #{addTime}, #{updateTime}, #{deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StorageDO storageDO);
}
