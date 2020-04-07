package com.hyf.backend.portal.controller;

import com.hyf.backend.portal.feign.ApiBannerClient;
import com.hyf.backend.portal.feign.ApiGoodsClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiHomeController {
    @Autowired
    private ApiGoodsClient goodsClient;
    @Autowired
    private ApiBannerClient bannerClient;

}
