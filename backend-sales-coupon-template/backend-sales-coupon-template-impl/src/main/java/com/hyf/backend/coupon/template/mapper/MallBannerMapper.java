package com.hyf.backend.coupon.template.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.coupon.template.dataobject.MallBanner;
import com.hyf.backend.coupon.template.dataobject.MallBannerExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallBannerMapper extends BaseMapperWithPK<MallBanner, MallBannerExample, Integer> {
    @Select("select * from mall_banner limit 5")
    List<MallBanner> selectByLimit5();
}
