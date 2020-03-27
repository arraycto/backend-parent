package com.hyf.backend.coupon.template.controller.admin;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.AdminCouponTemplateApi;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminCouponTemplateVO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class AdminCouponTemplateController implements AdminCouponTemplateApi {

    @Autowired
    @Qualifier("templateService")
    private CouponTemplateService couponTemplateService;

    @Override
    public ResponseVO<AdminCouponTemplateVO> createCouponTemplate(@RequestBody @Validated AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO) {
        CouponTemplateBO couponTemplate = couponTemplateService.createCouponTemplate(adminCreateCouponTemplateDTO);
        AdminCouponTemplateVO adminCouponTemplateVO = new AdminCouponTemplateVO();
        BeanUtils.copyProperties(couponTemplate, adminCouponTemplateVO);
        return ResponseVO.ok(adminCouponTemplateVO);
    }

    @Override
    public ResponseVO<PageVO<AdminCouponTemplateVO>> listCouponTemplate(AdminQueryCouponTemplateDTO queryCouponTemplateDTO) {
        PageListBO<CouponTemplateBO> couponTemplateBOPageListBO = couponTemplateService.listCouponTemplate(queryCouponTemplateDTO);
        List<AdminCouponTemplateVO> list = new ArrayList<>(couponTemplateBOPageListBO.getList().size());
        for (CouponTemplateBO couponTemplateBO : couponTemplateBOPageListBO.getList()) {
            AdminCouponTemplateVO vo = new AdminCouponTemplateVO();
            BeanUtils.copyProperties(couponTemplateBO, vo);
            list.add(vo);
        }
        return ResponseVO.ok(new PageVO<>(list,
                couponTemplateBOPageListBO.getPageSize(),
                couponTemplateBOPageListBO.getPageNo(),
                couponTemplateBOPageListBO.getTotal()));
    }


}
