package com.hyf.backend.portal.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.vo.ApiBannerVO;
import com.hyf.backend.portal.feign.ApiBannerClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
@Component
public class ApiBannerClientFallbackFactory implements FallbackFactory<ApiBannerClient> {
    @Override
    public ApiBannerClient create(Throwable cause) {
        return new ApiBannerClient() {
            @Override
            public ResponseVO<ListVO<ApiBannerVO>> listBanner() {
                log.error("调用banner出错啦....");
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }
        };
    }
}
