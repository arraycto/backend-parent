package com.hyf.backend.portal.controller;

import com.hyf.backend.common.annotation.CurrentUser;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.portal.controller.vo.CreateSettlementDTO;
import com.hyf.backend.portal.feign.ApiCartClient;
import com.hyf.backend.portal.feign.ApiCouponClient;
import com.hyf.backend.portal.feign.ApiGoodsClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/cart")
public class ApiCartSettlementController {
    @Autowired
    private ApiGoodsClient goodsClient;
    @Autowired
    private ApiCouponClient couponClient;
    @Autowired
    private ApiCartClient cartClient;

    @PostMapping("/settlement")
    public ResponseVO<Map<String, Object>> cartSettlement(@CurrentUser("uid") Integer uid, @RequestBody CreateSettlementDTO settlementDTO) {
        //1. 可用的优惠券
        Map<String, Object> resMap = new HashMap<>();
        settlementDTO.getCouponSettlement().setUid(uid);
        ResponseVO<ApiCouponSettlementVO> apiCouponSettlementVOResponseVO = couponClient.settlementCheckedGoods(settlementDTO.getCouponSettlement());
        ResponseVO<ListVO<ApiUserCartVO>> checkedGoods = cartClient.findCheckedGoods(uid);
        resMap.put("checkedGoodsList", checkedGoods.getData().getList());
        resMap.put("availableCouponLength", apiCouponSettlementVOResponseVO.getData().getUserAvailableCouponList().size());
        resMap.put("actualPrice", apiCouponSettlementVOResponseVO.getData().getCost());
        resMap.put("couponPrice", apiCouponSettlementVOResponseVO.getData().getDiscountPrice());
        resMap.put("goodsTotalPrice", apiCouponSettlementVOResponseVO.getData().getTotalGoodsPrice());
        resMap.put("orderTotalPrice", apiCouponSettlementVOResponseVO.getData().getCost());
        resMap.put("availableCouponList", apiCouponSettlementVOResponseVO.getData().getUserAvailableCouponList());
        resMap.put("maxDiscountCouponId",apiCouponSettlementVOResponseVO.getData().getMaxDiscountCouponId() );
        return ResponseVO.ok(resMap);
    }
}
