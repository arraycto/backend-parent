package com.hyf.backend.common.mybatis.mapper;

public interface BaseMapperWithPK<R, RE, K> extends BaseMapper<R, RE> {

    Integer deleteByPrimaryKey(K id);

    R selectByPrimaryKey(K id);

    Integer updateByPrimaryKeySelective(R record);

    Integer updateByPrimaryKey(R record);
}