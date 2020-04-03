package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.domain.QueryPageDTO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.api.dto.ApiQueryIdsDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDOExample;
import com.hyf.backend.coupon.template.mapper.CouponTemplateDOMapper;
import com.hyf.backend.coupon.template.service.AsyncService;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service("templateService")
@Slf4j
@Validated
public class TemplateServiceImpl implements CouponTemplateService {
    @Resource
    private CouponTemplateDOMapper couponTemplateMapper;
    @Autowired
    private AsyncService asyncService;

    @Override
    public CouponTemplateBO createCouponTemplate(AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO) {
        CouponTemplateDO couponTemplateDO = new CouponTemplateDO();
        BeanUtils.copyProperties(adminCreateCouponTemplateDTO, couponTemplateDO);
        couponTemplateDO.setIsExpired(false);
//        couponTemplateDO.setIsAvailable(true);
//        couponTemplateDO.setGoodsTypeLimitation(JSON.toJSONString(adminCreateCouponTemplateDTO.getGoodsTypeLimitation()));
//        couponTemplateDO.setWeight(JSON.toJSONString(adminCreateCouponTemplateDTO.getWeight()));
        couponTemplateDO.setExpirationCode(CouponTemplateExpirationEnum.of(adminCreateCouponTemplateDTO.getExpirationCode()));
        couponTemplateDO.setDiscountCategory(CouponDiscountCategoryEnum.of(adminCreateCouponTemplateDTO.getDiscountCategory()));
//        CouponTemplateDO.setCreateUserId(Integer.valueOf(ContextHolder.getCurrentContext().get(Constant.X_ADMIN_UID)));
        couponTemplateDO.setTemplateKey(new DateTime().toString("yyyyMMdd") + RandomStringUtils.randomAlphabetic(10));
//        CouponTemplateDO.setGoodsTypeLimitation(JSON.toJSONString(adminCreateCouponTemplateDTO.getGoodsTypeLimitation()));
//        CouponTemplateDO.setWeight(JSON.toJSONString(adminCreateCouponTemplateDTO.getWeight()));
        couponTemplateMapper.insertSelective(couponTemplateDO);
        asyncService.asyncGenerateKeyByCouponTemplate(couponTemplateDO);
        return new CouponTemplateBO(couponTemplateDO);
    }

    @Override
    public PageListBO<CouponTemplateBO> listCouponTemplate(AdminQueryCouponTemplateDTO queryCouponTemplateDTO) {
        CouponTemplateDOExample example = new CouponTemplateDOExample();
        CouponTemplateDOExample.Criteria criteria = example.createCriteria();
        return new PageListBO<>(
                MapperHelper.selectPageByQuery(couponTemplateMapper, example, criteria, queryCouponTemplateDTO, queryCouponTemplateDTO.toPage()),
                CouponTemplateBO::new);
    }

    @Override
    public List<CouponTemplateBO> listCouponTemplateAll() {
        return couponTemplateMapper.selectSimple().stream()
                .map(CouponTemplateBO::new).collect(Collectors.toList());
    }

    @Override
    public List<CouponTemplateBO> findAvailableCouponTemplate(QueryPageDTO queryPageDTO) {
        CouponTemplateDOExample example = new CouponTemplateDOExample();
        CouponTemplateDOExample.Criteria criteria = example.createCriteria().andIsAvailableEqualTo(true)
                .andIsExpiredEqualTo(false);
        List<CouponTemplateDO> couponTemplateDOS = couponTemplateMapper.selectByExample(example);
        return couponTemplateDOS.stream().map(CouponTemplateBO::new).collect(Collectors.toList());
//
    }

    @Override
    public List<CouponTemplateBO> findByIds(@Valid ApiQueryIdsDTO apiQueryIdsDTO) {
        CouponTemplateDOExample example = new CouponTemplateDOExample();
        example.createCriteria().andIdIn(apiQueryIdsDTO.getIds());
        List<CouponTemplateDO> couponTemplateDOS = couponTemplateMapper.selectByExample(example);
        return couponTemplateDOS.stream().map(CouponTemplateBO::new).collect(Collectors.toList());
    }
}
