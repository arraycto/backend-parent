package com.hyf.backend.common.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface BaseMapper<R, RE> {
    Long countByExample(RE example);

    Integer deleteByExample(RE example);

    Integer insert(R record);

    Integer insertSelective(R record);

    List<R> selectByExample(RE example);

    Integer updateByExampleSelective(@Param("record") R record, @Param("example") RE example);

    Integer updateByExample(@Param("record") R record, @Param("example") RE example);
}
