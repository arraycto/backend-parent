package com.hyf.backend.coupon.template.controller.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.AdminBannerApi;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerCreateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerQueryDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerUpdateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminBannerVO;
import com.hyf.backend.coupon.template.service.BannerService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class AdminBannerController implements AdminBannerApi {
    @Autowired
    private BannerService service;

    @Override
    public ResponseVO<PageVO<AdminBannerVO>> findPageByQuery( AdminBannerQueryDTO queryDTO) {
        PageVO<AdminBannerVO> pageByQuery = service.findPageByQuery(queryDTO);
        return ResponseVO.ok(pageByQuery);
    }

    @Override
    public ResponseVO<AdminBannerVO> update(@RequestBody AdminBannerUpdateDTO updateDTO) {
        return ResponseVO.ok(service.updateBanner(updateDTO));
    }

    @Override
    public ResponseVO<AdminBannerVO> create(@RequestBody AdminBannerCreateDTO createDTO) {
        return ResponseVO.ok(service.createBanner(createDTO));
    }

    @Override
    public ResponseVO<AdminBannerVO> get(AdminBannerQueryDTO dto) {
        return null;
    }
}
