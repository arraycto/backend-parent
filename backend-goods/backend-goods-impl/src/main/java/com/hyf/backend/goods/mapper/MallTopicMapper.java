package com.hyf.backend.goods.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPKAndBlobs;
import com.hyf.backend.goods.dataobject.MallTopic;
import com.hyf.backend.goods.dataobject.MallTopicExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallTopicMapper extends BaseMapperWithPKAndBlobs<MallTopic, MallTopicExample, Integer> {
    @Select("select * from mall_topic limit 5")
    List<MallTopic> selectLimit5();
}
