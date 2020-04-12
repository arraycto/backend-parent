package com.hyf.backend.coupon.template.service;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerCreateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerQueryDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerUpdateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminBannerVO;
import com.hyf.backend.coupon.template.dataobject.MallBanner;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface BannerService {

    AdminBannerVO createBanner(@Valid AdminBannerCreateDTO bannerCreateDTO);

    AdminBannerVO updateBanner(@Valid AdminBannerUpdateDTO bannerUpdateDTO);

    PageVO<AdminBannerVO> findPageByQuery(@Valid AdminBannerQueryDTO queryDTO);

    List<MallBanner> listBanner();
}
