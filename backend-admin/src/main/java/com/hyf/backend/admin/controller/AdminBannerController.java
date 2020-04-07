package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminBannerClient;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerCreateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerQueryDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerUpdateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminBannerVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/banner")
public class AdminBannerController {
    @Autowired
    private AdminBannerClient bannerClient;

    @PostMapping("/create")
    @RequiresPermissions("admin:banner:create")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "创建")
    public ResponseVO<AdminBannerVO> createBanner(@RequestBody AdminBannerCreateDTO createDTO) {
        return bannerClient.create(createDTO);
    }

    @GetMapping("/list")
    @RequiresPermissions("admin:banner:list")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "列表")
    public ResponseVO<PageVO<AdminBannerVO>> findPageByQuery(AdminBannerQueryDTO queryDTO) {
        return bannerClient.findPageByQuery(queryDTO);
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:banner:update")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "修改")
    public ResponseVO<AdminBannerVO> update(@RequestBody AdminBannerUpdateDTO updateDTO) {
        return bannerClient.update(updateDTO);
    }
}
