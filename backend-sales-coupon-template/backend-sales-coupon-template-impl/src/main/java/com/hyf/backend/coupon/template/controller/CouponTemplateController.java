package com.hyf.backend.coupon.template.controller;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.dto.ApiQueryIdsDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.feign.UserServiceApiClient;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@Slf4j
@RequestMapping("/coupon/template")
public class CouponTemplateController {
    @Autowired
    private UserServiceApiClient userServiceApiClient;
    @Autowired
    @Qualifier("templateService")
    private CouponTemplateService couponTemplateService;

    @Value("${test.name:hahaha}")
    private String name;

    @GetMapping("/get")
    public String get(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
            log.info("headerName: {}, headerValue: {}", s, header);
        }
        return "Hello Docker";
    }

    @GetMapping("/getUser")
    public ResponseVO<String> getUser(HttpServletRequest request) {
        log.info("request remoteAdder: {}", request.getRemoteAddr());
        log.info("uid: {}", ContextHolder.getCurrentContext().get(Constant.X_UID));
//        ResponseVO<String> hello = userServiceApiClient.hello("haahhahahaha");
//        if (hello.isOk()) {
//            return hello;
//        } else {
//            throw new BizException(-1, hello.getMsg());
//        }
//        String user = baseTemplateService.getUser();
//        log.info("data: {}", user);
        return ResponseVO.ok("hahaha");
    }

    @GetMapping("/test")
    public String test(String code) {
        log.info("收到的code参数: {}", code);
        return name;
    }

    @PostMapping("/list-available")
    public ListVO<ApiCouponTemplateVO> listAvailableCouponTemplate() {
        List<CouponTemplateBO> availableCouponTemplate =
                couponTemplateService.findAvailableCouponTemplate(null);

        List<ApiCouponTemplateVO> voList = new ArrayList<>(availableCouponTemplate.size());
        for (CouponTemplateBO couponTemplateBO : availableCouponTemplate) {
            ApiCouponTemplateVO apiCouponTemplateVO = new ApiCouponTemplateVO();
            BeanUtils.copyProperties(couponTemplateBO, apiCouponTemplateVO);
            voList.add(apiCouponTemplateVO);
        }
        return new ListVO<>(voList);
//        return ResponseVO.ok(new PageVO<>(voList, availableCouponTemplate.getPageSize(), availableCouponTemplate.getPageNo(), availableCouponTemplate.getTotal()));
    }

    @PostMapping("/list-by-ids")
    public ResponseVO<ListVO<ApiCouponTemplateVO>> listByIds(@RequestBody @Validated ApiQueryIdsDTO apiQueryIdsDTO) {
        return ResponseVO.ok(new ListVO<>(boToVO(couponTemplateService.findByIds(apiQueryIdsDTO))));
    }

    private List<ApiCouponTemplateVO> boToVO(List<CouponTemplateBO> boList) {
        List<ApiCouponTemplateVO> voList = new ArrayList<>(boList.size());
        for (CouponTemplateBO bo : boList) {
            ApiCouponTemplateVO apiCouponTemplateVO = new ApiCouponTemplateVO();
            BeanUtils.copyProperties(bo, apiCouponTemplateVO);
            voList.add(apiCouponTemplateVO);
        }
        return voList;
    }

}
