package com.hyf.backend.goods.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.goods.dataobject.MallGoodsCategory;
import com.hyf.backend.goods.dataobject.MallGoodsCategoryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsCategoryMapper extends BaseMapperWithPK<MallGoodsCategory, MallGoodsCategoryExample, Integer> {
    @Select("select * from mall_category where pid = #{pid}")
    List<MallGoodsCategory> selectByPid(@Param("pid") Integer pid);

    @Select("select * from mall_category where level = #{level} and pid = #{pid}")
    List<MallGoodsCategory> selectByLevelAndPid(@Param("level") String level, @Param("pid") Integer pid);

}
