package com.hyf.backend.coupon.template.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.vo.ApiBannerVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/banner")
public interface ApiBanner {
    @GetMapping("/list")
    ResponseVO<ListVO<ApiBannerVO>> listBanner();
}
