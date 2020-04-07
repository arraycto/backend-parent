package com.hyf.backend.goods.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.goods.dataobject.MallGoodsAttr;
import com.hyf.backend.goods.dataobject.MallGoodsAttrExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsAttrMapper extends BaseMapperWithPK<MallGoodsAttr, MallGoodsAttrExample, Integer> {
    @Delete("delete from mall_goods_attribute  where goods_id = #{goodsId}")
    int deleteByGoodsId(@Param("goodsId") Integer goodsId);
}
