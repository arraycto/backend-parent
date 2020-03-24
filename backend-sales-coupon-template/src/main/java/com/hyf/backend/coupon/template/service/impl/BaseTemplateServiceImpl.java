package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.feign.UserServiceApiClient;
import com.hyf.backend.coupon.template.service.BaseTemplateService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Elvis on 2020/3/19
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
@Slf4j
public class BaseTemplateServiceImpl implements BaseTemplateService {
    @Autowired
    private UserServiceApiClient userServiceApiClient;

    @Override
    public String getUser() {
        ResponseVO<String> responseVO = userServiceApiClient.hello("hahaha");
        if (responseVO.isOk()) {
            log.info("调用成功, data:{}", responseVO.getData());
        } else {
            throw new BizException(responseVO.getMsg());
        }
        return responseVO.getData();
    }
}
