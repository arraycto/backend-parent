package com.hyf.backend.coupon.template.controller;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.api.dto.ApiListByStatusDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/3
 * @Email: yfelvis@gmail.com
 * @Desc: 查询的过程中涉及到动态计算
 */
@RestController
@RequestMapping("/user/coupon")
@Validated
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    @PostMapping("/list-by-status")
    public ResponseVO<PageVO<UserCouponBO>> listByStatus(@RequestBody ApiListByStatusDTO apiListByStatusDTO) {
        Long uid = Long.valueOf(ContextHolder.getCurrentContext().get(Constant.X_UID));
        PageListBO<UserCouponBO> userCouponBOPageListBO =
                userCouponService.listByUidAndType(uid, apiListByStatusDTO.getStatus(), apiListByStatusDTO);
        return ResponseVO.ok(new PageVO<>(userCouponBOPageListBO.getList(), userCouponBOPageListBO.getPageSize(), userCouponBOPageListBO.getPageNo(), userCouponBOPageListBO.getTotal()));
    }

    @PostMapping("/list-available-template")
    public ResponseVO<ListVO<CouponTemplateBO>> listAvailableTemplate() {
        Long uid = Long.valueOf(ContextHolder.getCurrentContext().get(Constant.X_UID));
        List<CouponTemplateBO> availableTemplate =
                userCouponService.findAvailableTemplate(uid, null);
        return ResponseVO.ok(new ListVO<>(availableTemplate));
    }

    @PostMapping("/acquire")
    public ResponseVO<UserCouponBO> acquireCoupon(@RequestParam Long templateId) {
            Long uid = Long.valueOf(ContextHolder.getCurrentContext().get(Constant.X_UID));
        UserCouponBO userCouponBO = userCouponService.acquireCoupon(uid, templateId);
        return ResponseVO.ok(userCouponBO);
    }
}
