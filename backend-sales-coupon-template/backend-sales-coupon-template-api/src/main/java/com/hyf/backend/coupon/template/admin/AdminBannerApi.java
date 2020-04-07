package com.hyf.backend.coupon.template.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerCreateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerQueryDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerUpdateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminBannerVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/banner")
public interface AdminBannerApi {
    @PostMapping("list")
    ResponseVO<PageVO<AdminBannerVO>> findPageByQuery(@RequestBody @Validated AdminBannerQueryDTO queryDTO);

    @PostMapping("/update")
    ResponseVO<AdminBannerVO> update(@RequestBody @Validated AdminBannerUpdateDTO updateDTO);

    @PostMapping("/create")
    ResponseVO<AdminBannerVO> create(@RequestBody @Validated AdminBannerCreateDTO createDTO);
}
