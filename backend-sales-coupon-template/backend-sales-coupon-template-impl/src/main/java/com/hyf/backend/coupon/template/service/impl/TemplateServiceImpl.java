package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDOExample;
import com.hyf.backend.coupon.template.mapper.CouponTemplateDOMapper;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service("templateService")
@Slf4j
public class TemplateServiceImpl implements CouponTemplateService {
    @Resource
    private CouponTemplateDOMapper couponTemplateMapper;

    @Override
    public CouponTemplateBO createCouponTemplate(AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO) {
        CouponTemplateDO CouponTemplateDO = new CouponTemplateDO();
        BeanUtils.copyProperties(adminCreateCouponTemplateDTO, CouponTemplateDO);
        CouponTemplateDO.setIsExpired(false);
        CouponTemplateDO.setIsAvailable(true);
//        CouponTemplateDO.setCreateUserId(Integer.valueOf(ContextHolder.getCurrentContext().get(Constant.X_ADMIN_UID)));
        CouponTemplateDO.setTemplateKey(new DateTime().toString("yyyyMMdd") + RandomStringUtils.randomAlphabetic(10));
//        CouponTemplateDO.setGoodsTypeLimitation(JSON.toJSONString(adminCreateCouponTemplateDTO.getGoodsTypeLimitation()));
//        CouponTemplateDO.setWeight(JSON.toJSONString(adminCreateCouponTemplateDTO.getWeight()));
        couponTemplateMapper.insertSelective(CouponTemplateDO);
        return new CouponTemplateBO(CouponTemplateDO);
    }

    @Override
    public PageListBO<CouponTemplateBO> listCouponTemplate(AdminQueryCouponTemplateDTO queryCouponTemplateDTO) {
        CouponTemplateDOExample example = new CouponTemplateDOExample();
        CouponTemplateDOExample.Criteria criteria = example.createCriteria();
        return new PageListBO<>(
                MapperHelper.selectPageByQuery(couponTemplateMapper, example, criteria, queryCouponTemplateDTO, queryCouponTemplateDTO.toPage()),
                CouponTemplateBO::new);
    }
}
