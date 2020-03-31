package com.hyf.backend.coupon.template.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDOExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author huyufei
 * @since 2020-03-26
 */
public interface CouponTemplateDOMapper extends BaseMapperWithPK<CouponTemplateDO, CouponTemplateDOExample, Long> {

    @Select("select id, title, template_key from sales_coupon_template")
    List<CouponTemplateDO> selectSimple();
}
