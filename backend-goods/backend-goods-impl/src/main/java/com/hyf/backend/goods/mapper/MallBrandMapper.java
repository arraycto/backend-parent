package com.hyf.backend.goods.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.goods.dataobject.MallBrand;
import com.hyf.backend.goods.dataobject.MallBrandExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallBrandMapper extends BaseMapperWithPK<MallBrand, MallBrandExample, Integer> {
    @Select("select * from mall_brand")
    List<MallBrand> selectAll();
}
