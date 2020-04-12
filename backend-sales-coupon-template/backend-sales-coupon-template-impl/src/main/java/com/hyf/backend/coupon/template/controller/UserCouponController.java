package com.hyf.backend.coupon.template.controller;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.api.ApiCoupon;
import com.hyf.backend.coupon.template.api.dto.ApiAcquireCouponDTO;
import com.hyf.backend.coupon.template.api.dto.ApiCartIdReq;
import com.hyf.backend.coupon.template.api.dto.ApiCouponSettlementDTO;
import com.hyf.backend.coupon.template.api.dto.ApiListByStatusDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.coupon.template.api.vo.ApiUserCouponVO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/3
 * @Email: yfelvis@gmail.com
 * @Desc: 查询的过程中涉及到动态计算
 */
@RestController
@Validated
public class UserCouponController implements ApiCoupon {

    @Autowired
    private UserCouponService userCouponService;

    @PostMapping("/list-by-status")
    public ResponseVO<PageVO<ApiUserCouponVO>> listByStatus(@RequestBody ApiListByStatusDTO apiListByStatusDTO) {
        String s = ContextHolder.getCurrentContext().get(Constant.X_UID);
        Long uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Long.valueOf(s);
        }
        if (uid == null) {
            throw new BizException("请登录");
        }
        PageListBO<UserCouponBO> userCouponBOPageListBO =
                userCouponService.listByUidAndType(uid, apiListByStatusDTO.getStatus(), apiListByStatusDTO);

        List<ApiUserCouponVO> voList = new ArrayList<>();
        for (UserCouponBO bo : userCouponBOPageListBO.getList()) {
            ApiUserCouponVO vo = new ApiUserCouponVO();
            BeanUtils.copyProperties(bo, vo);
            ApiCouponTemplateVO templateVO = new ApiCouponTemplateVO();
            BeanUtils.copyProperties(bo.getCouponTemplateBO(), templateVO);
            vo.setCouponTemplate(templateVO);
            voList.add(vo);
        }
        return ResponseVO.ok(new PageVO<>(voList, userCouponBOPageListBO.getPageSize(), userCouponBOPageListBO.getPageNo(), userCouponBOPageListBO.getTotal()));
    }

    @Override
    public ResponseVO<ApiCouponSettlementVO> settlementCheckedGoods(ApiCouponSettlementDTO settlementDTO) {
        String s = ContextHolder.getCurrentContext().get(Constant.X_UID);
        Long uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Long.valueOf(s);
        }
        if (uid == null) {
            throw new BizException("请登录");
        }
        settlementDTO.setUid(Math.toIntExact(uid));
        return ResponseVO.ok(userCouponService.settlementCheckedGoods(settlementDTO));
    }


    public ResponseVO<ListVO<ApiCouponTemplateVO>> listAvailableTemplate(@RequestParam(value = "uid", required = false) Long uid) {
        List<CouponTemplateBO> availableTemplate =
                userCouponService.findAvailableTemplate(uid, null);
        List<ApiCouponTemplateVO> voList = new ArrayList<>();
        for (CouponTemplateBO bo : availableTemplate) {
            ApiCouponTemplateVO vo = new ApiCouponTemplateVO();
            BeanUtils.copyProperties(bo, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @PostMapping("/acquire")
    public ResponseVO<UserCouponBO> acquireCoupon(@RequestBody ApiAcquireCouponDTO couponDTO) {
        Long uid = Long.valueOf(ContextHolder.getCurrentContext().get(Constant.X_UID));
        UserCouponBO userCouponBO = userCouponService.acquireCoupon(uid, couponDTO.getTemplateId());
        return ResponseVO.ok(userCouponBO);
    }

    @Override
    public ResponseVO<ListVO<ApiUserCouponVO>> listMatchCartGoods(@RequestBody ApiCartIdReq req) {
        String s = ContextHolder.getCurrentContext().get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Integer.valueOf(s);
        }
        if (uid == null) {
            throw new BizException("请登录");
        }
        return new ResponseVO<>(new ListVO<>(userCouponService.listMatchCartGoodsCouponList(uid, req.getCartId())));
    }
}
