package com.hyf.backend.portal.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.api.vo.ApiTopicVO;
import com.hyf.backend.portal.feign.ApiGoodsClient;
import com.hyf.backend.portal.feign.ApiTopicClient;
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
@Component
@Slf4j
public class ApiTopicClientFallbackFactory implements FallbackFactory<ApiTopicClient> {
    @Override
    public ApiTopicClient create(Throwable cause) {
       return new ApiTopicClient() {
           @Override
           public ResponseVO<ListVO<ApiTopicVO>> listTopicLimit() {
               return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
           }
       };
    }
}
