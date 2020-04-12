package com.hyf.backend.portal.controller;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.vo.ApiBannerVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.goods.api.vo.ApiBrandVO;
import com.hyf.backend.goods.api.vo.ApiCategoryCurrentVO;
import com.hyf.backend.goods.api.vo.ApiGoodsCategoryVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.api.vo.ApiTopicVO;
import com.hyf.backend.portal.feign.ApiBannerClient;
import com.hyf.backend.portal.feign.ApiBrandClient;
import com.hyf.backend.portal.feign.ApiCategoryClient;
import com.hyf.backend.portal.feign.ApiCouponClient;
import com.hyf.backend.portal.feign.ApiGoodsClient;
import com.hyf.backend.portal.feign.ApiTopicClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/home")
public class ApiHomeController {
    @Autowired
    private ApiGoodsClient goodsClient;
    @Autowired
    private ApiBannerClient bannerClient;
    @Autowired
    private ApiCategoryClient categoryClient;
    @Autowired
    private ApiCouponClient apiCouponClient;
    @Autowired
    private ApiBrandClient brandClient;
    @Autowired
    private ApiTopicClient topicClient;

    @GetMapping("/index")
    public ResponseVO<Map<String, Object>> home() throws ExecutionException, InterruptedException {
        String uids = ContextHolder.getCurrentContext().get(Constant.X_UID);
        Long uid = null;
        if (StringUtils.isNotEmpty(uids)) {
            uid = Long.valueOf(uids);
        }
        CompletableFuture<ResponseVO<ListVO<ApiBannerVO>>> apiBannerFuture = CompletableFuture.supplyAsync(() -> bannerClient.listBanner());
        CompletableFuture<ResponseVO<ListVO<ApiGoodsCategoryVO>>> apiCategoryFuture = CompletableFuture.supplyAsync(() -> categoryClient.listL1());
        Long finalUid = uid;
        CompletableFuture<ResponseVO<ListVO<ApiCouponTemplateVO>>> apiCouponFuture = CompletableFuture.supplyAsync(() -> apiCouponClient.listAvailableTemplate(finalUid));
        CompletableFuture<ResponseVO<ListVO<ApiBrandVO>>> apiBrandFuture = CompletableFuture.supplyAsync(() -> brandClient.listAll());
        CompletableFuture<ResponseVO<ListVO<ApiGoodsVO>>> apiGoodsNewFuture = CompletableFuture.supplyAsync(() -> goodsClient.listByNew());
        CompletableFuture<ResponseVO<ListVO<ApiGoodsVO>>> apiGoodsHotFuture = CompletableFuture.supplyAsync(() -> goodsClient.listByHot());
        CompletableFuture<ResponseVO<ListVO<ApiTopicVO>>> apiTopicFuture = CompletableFuture.supplyAsync(() -> topicClient.listTopicLimit());
        CompletableFuture.allOf(apiBannerFuture, apiCategoryFuture, apiCouponFuture, apiBrandFuture, apiGoodsHotFuture, apiGoodsNewFuture, apiTopicFuture).get();
        ResponseVO<ListVO<ApiBannerVO>> apiBannerVORes = apiBannerFuture.get();
        ResponseVO<ListVO<ApiGoodsCategoryVO>> apiCategoryRes = apiCategoryFuture.get();
        ResponseVO<ListVO<ApiCouponTemplateVO>> couponRes = apiCouponFuture.get();
        ResponseVO<ListVO<ApiBrandVO>> brandRes = apiBrandFuture.get();
        ResponseVO<ListVO<ApiGoodsVO>> brandNewRes = apiGoodsNewFuture.get();
        ResponseVO<ListVO<ApiGoodsVO>> brandHotRes = apiGoodsHotFuture.get();
        ResponseVO<ListVO<ApiTopicVO>> topicRes = apiTopicFuture.get();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("banner", apiBannerVORes.getData().getList());
        resultMap.put("channel", apiCategoryRes.getData().getList());
        resultMap.put("couponList", couponRes.getData().getList());
        resultMap.put("brandList", brandRes.getData().getList());
        resultMap.put("hotGoodsList", brandHotRes.getData().getList());
        resultMap.put("newGoodsList", brandNewRes.getData().getList());
        resultMap.put("topicList", topicRes.getData().getList());
        return ResponseVO.ok(resultMap);

    }

    @GetMapping("/navbar-list-byl1")
    public ResponseVO<ListVO<ApiGoodsCategoryVO>> listNavBarByL1(Integer l1id) {
        return categoryClient.listNavBarByL1(l1id);
    }

    @GetMapping("/navbar-list-byl2")
    public ResponseVO<ApiCategoryCurrentVO> listNavBarByL2(Integer l2id) {
        return categoryClient.listNavBarByL2(l2id);
    }

}
