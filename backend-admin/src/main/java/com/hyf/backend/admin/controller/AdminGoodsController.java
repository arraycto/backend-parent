package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminGoodsClient;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsVO;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/goods")
public class AdminGoodsController {
    @Autowired
    private AdminGoodsClient goodsClient;

    @GetMapping("/list")
    @RequiresPermissions("admin:goods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "列表")
    public ResponseVO findPageByQuery(GoodsQueryDTO adminGoodsQueryDTO) {
        ResponseVO<PageVO<AdminGoodsVO>> pageVOResponseVO = goodsClient.findPageByQuery(adminGoodsQueryDTO);
        if (pageVOResponseVO.isOk()) {
            return pageVOResponseVO;
        } else {
            return ResponseVO.error(pageVOResponseVO.getMsg());
        }
    }

    @PostMapping("/create")
    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "创建")
    public ResponseVO createGoods(@RequestBody AdminGoodsAggregationTO adminGoodsAggregationTO) {
        return goodsClient.createGoods(adminGoodsAggregationTO);
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:goods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "修改")
    public ResponseVO updateGoods(@RequestBody AdminGoodsAggregationTO adminGoodsAggregationTO) {
        return goodsClient.updateGoods(adminGoodsAggregationTO);
    }

    @GetMapping("/list-category-brand")
    public ResponseVO listCategoryBrand() {
        ResponseVO<Map<String, Object>> mapResponseVO = goodsClient.listBrandAndCategory();
        return mapResponseVO;
    }

    @GetMapping("/goods-detail")
    public ResponseVO goodsDetail(@RequestParam("id") Integer id) {
        return goodsClient.goodsDetail(id);
    }
}
