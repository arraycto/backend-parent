package com.hyf.backend.coupon.template.controller;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.ApiBanner;
import com.hyf.backend.coupon.template.api.vo.ApiBannerVO;
import com.hyf.backend.coupon.template.dataobject.MallBanner;
import com.hyf.backend.coupon.template.service.BannerService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class BannerController implements ApiBanner {

    @Autowired
    private BannerService bannerService;

    @Override
    public ResponseVO<ListVO<ApiBannerVO>> listBanner() {
        List<MallBanner> mallBanners = bannerService.listBanner();
        List<ApiBannerVO> voList = new ArrayList<>();
        for (MallBanner mallBanner : mallBanners) {
            ApiBannerVO vo = new ApiBannerVO();
            BeanUtils.copyProperties(mallBanner, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }
}
