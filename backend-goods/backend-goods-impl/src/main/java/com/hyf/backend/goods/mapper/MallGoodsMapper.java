package com.hyf.backend.goods.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPKAndBlobs;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsExample;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsMapper extends BaseMapperWithPKAndBlobs<MallGoods, MallGoodsExample, Integer> {
    @Select("select * from mall_goods where name = #{name} limit 1")
    MallGoods selectByName(String name);
}
