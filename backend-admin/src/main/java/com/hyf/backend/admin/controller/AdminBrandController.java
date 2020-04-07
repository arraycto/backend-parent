package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminGoodsBrandClient;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminBrandVO;
import com.hyf.backend.goods.dto.AdminBrandCreateDTO;
import com.hyf.backend.goods.dto.AdminBrandQueryDTO;
import com.hyf.backend.goods.dto.AdminBrandUpdateDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/goods/brand")
public class AdminBrandController {

    @Autowired
    private AdminGoodsBrandClient goodsBrandClient;

    @PostMapping("/create")
    @RequiresPermissions("admin:goods:brand:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "创建")
    public ResponseVO<AdminBrandVO> createBrand(@RequestBody AdminBrandCreateDTO brandCreateDTO) {
        ResponseVO<AdminBrandVO> brand = goodsBrandClient.createBrand(brandCreateDTO);
        if (brand.isOk()) {
            return brand;
        } else {
            throw new BizException(brand.getMsg());
        }
    }

    @GetMapping("/list")
    @RequiresPermissions("admin:goods:brand:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "列表")
    public ResponseVO<PageVO<AdminBrandVO>> listBrand(AdminBrandQueryDTO brandQueryDTO) {
        ResponseVO<PageVO<AdminBrandVO>> pageVOResponseVO = goodsBrandClient.listBrand(brandQueryDTO);
        if (pageVOResponseVO.isOk()) {
            return pageVOResponseVO;
        } else {
            throw new BizException(pageVOResponseVO.getMsg());
        }
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:goods:brand:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "更新")
    public ResponseVO<AdminBrandVO> updateBrand(@RequestBody AdminBrandUpdateDTO brandUpdateDTO) {
        ResponseVO<AdminBrandVO> adminBrandVOResponseVO = goodsBrandClient.updateBrand(brandUpdateDTO);
        if (adminBrandVOResponseVO.isOk()) {
            return adminBrandVOResponseVO;
        } else {
            throw new BizException(adminBrandVOResponseVO.getMsg());
        }
    }


}
