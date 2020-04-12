package com.hyf.backend.coupon.template.api;

import com.hyf.backend.common.annotation.CurrentUser;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.api.dto.ApiCartIdReq;
import com.hyf.backend.coupon.template.api.dto.ApiCouponSettlementDTO;
import com.hyf.backend.coupon.template.api.dto.ApiListByStatusDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.coupon.template.api.vo.ApiUserCouponVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/coupon")
public interface ApiCoupon {

    @PostMapping("/list-available-template")
    ResponseVO<ListVO<ApiCouponTemplateVO>> listAvailableTemplate(@RequestParam(value = "uid", required = false) Long uid);

    @PostMapping("/list-by-status")
    ResponseVO<PageVO<ApiUserCouponVO>> listByStatus(@RequestBody ApiListByStatusDTO apiListByStatusDTO);

    @PostMapping("/settlement")
    ResponseVO<ApiCouponSettlementVO> settlementCheckedGoods(@RequestBody ApiCouponSettlementDTO settlementDTO);

    @PostMapping("/find-match-cart-goods")
    ResponseVO<ListVO<ApiUserCouponVO>> listMatchCartGoods(@RequestBody ApiCartIdReq cartIdReq);
}
