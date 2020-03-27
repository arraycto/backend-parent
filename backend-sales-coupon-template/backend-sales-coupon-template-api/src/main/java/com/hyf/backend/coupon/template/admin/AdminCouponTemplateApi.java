package com.hyf.backend.coupon.template.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminCreateCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminCouponTemplateVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/coupon/template")
public interface AdminCouponTemplateApi {
    @PostMapping("/create")
    ResponseVO<AdminCouponTemplateVO> createCouponTemplate(@RequestBody @Validated AdminCreateCouponTemplateDTO adminCreateCouponTemplateDTO);

    @PostMapping("/list")
    ResponseVO<PageVO<AdminCouponTemplateVO>> listCouponTemplate(@RequestBody @Validated AdminQueryCouponTemplateDTO queryCouponTemplateDTO);
}
