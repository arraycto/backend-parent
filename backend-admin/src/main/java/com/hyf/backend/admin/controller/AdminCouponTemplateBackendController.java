package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminCouponTemplateClient;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminCouponTemplateVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/coupon/template")
public class AdminCouponTemplateBackendController {

    @Autowired
    private AdminCouponTemplateClient adminCouponTemplateClient;

    @PostMapping("/create")
    @RequiresPermissions("admin:coupon:template:create")
    @RequiresPermissionsDesc(menu = {"促销管理", "优惠券模板管理"}, button = "创建")
    public ResponseVO createAdminCouponTemplate(@RequestBody @Validated AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO) {
        ResponseVO<AdminCouponTemplateVO> couponTemplate = adminCouponTemplateClient.createCouponTemplate(adminCreateCouponTemplateDTO);
        if (couponTemplate.isOk()) {
            return couponTemplate;
        } else {
            return ResponseVO.error(couponTemplate.getMsg());
        }
    }

    @GetMapping("/list")
    @RequiresPermissions("admin:coupon:template:list")
    @RequiresPermissionsDesc(menu = {"促销管理", "优惠券模板管理"}, button = "查询")
    public ResponseVO listAdminCouponTemplate(AdminQueryCouponTemplateDTO adminQueryCouponTemplateDTO) {
        ResponseVO<PageVO<AdminCouponTemplateVO>> pageVOResponseVO = adminCouponTemplateClient.listCouponTemplate(adminQueryCouponTemplateDTO);
        if (pageVOResponseVO.isOk()) {
            return pageVOResponseVO;
        } else {
            return ResponseVO.error(pageVOResponseVO.getMsg());
        }
    }

    @GetMapping("/list-all")
    public ResponseVO listAdminCouponTemplateAll() {
        ResponseVO<ListVO<AdminCouponTemplateVO>> listVOResponseVO = adminCouponTemplateClient.listCouponTemplateAll();
        if (listVOResponseVO.isOk()) {
            return listVOResponseVO;
        } else {
            return ResponseVO.error(listVOResponseVO.getMsg());
        }
    }
}
