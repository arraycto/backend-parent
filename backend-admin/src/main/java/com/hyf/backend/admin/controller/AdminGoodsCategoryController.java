package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminGoodsCategoryClient;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsCategoryVO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/goods/category")
public class AdminGoodsCategoryController {

    @Autowired
    private AdminGoodsCategoryClient goodsCategoryClient;

    @PostMapping("/create")
    @RequiresPermissions("admin:goods:category:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "分类管理"}, button = "创建")
    public ResponseVO createGoodsCategory(@RequestBody AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
        ResponseVO<AdminGoodsCategoryVO> goodsCategory = goodsCategoryClient.createGoodsCategory(createGoodsCategoryDTO);
        if (goodsCategory.isOk()) {
            return goodsCategory;
        } else {
            return ResponseVO.error(goodsCategory.getMsg());
        }
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:goods:category:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "分类管理"}, button = "更新")
    public ResponseVO updateGoodsCategory(@RequestBody AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO) {
        ResponseVO<AdminGoodsCategoryVO> adminGoodsCategoryVOResponseVO = goodsCategoryClient.updateGoodsCategory(updateGoodsCategoryDTO);
        if (adminGoodsCategoryVOResponseVO.isOk()) {
            return adminGoodsCategoryVOResponseVO;
        } else {
            return ResponseVO.error(adminGoodsCategoryVOResponseVO.getMsg());
        }
    }

    @GetMapping("/list")
    @RequiresPermissions("admin:goods:category:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "分类管理"}, button = "列表")
    public ResponseVO listGoodsCategory(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO) {
        ResponseVO<PageVO<AdminGoodsCategoryVO>> pageByQuery = goodsCategoryClient.findPageByQuery(queryGoodsCategoryDTO);
        if (pageByQuery.isOk()) {
            return pageByQuery;
        } else {
            return ResponseVO.error(pageByQuery.getMsg());
        }
    }

    @GetMapping("/list-level1")
    public ResponseVO listLevel1() {
        ResponseVO<ListVO<AdminGoodsCategoryVO>> listVOResponseVO = goodsCategoryClient.listLevel1();
        if (listVOResponseVO.isOk()) {
            ListVO<AdminGoodsCategoryVO> data = listVOResponseVO.getData();
            List<Map<String, Object>> r = new ArrayList<>();
            for (AdminGoodsCategoryVO vo : data.getList()) {
                Map<String, Object> valueMap = new HashMap<>();
                valueMap.put("value", vo.getId());
                valueMap.put("label", vo.getName());
                r.add(valueMap);
            }
            return ResponseVO.ok(new ListVO<>(r));
        } else {
            return ResponseVO.error(listVOResponseVO.getMsg());
        }
    }


}
