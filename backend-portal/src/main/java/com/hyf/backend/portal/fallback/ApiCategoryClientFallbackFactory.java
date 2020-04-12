package com.hyf.backend.portal.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiCategoryCurrentVO;
import com.hyf.backend.goods.api.vo.ApiGoodsCategoryVO;
import com.hyf.backend.portal.feign.ApiCategoryClient;
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
public class ApiCategoryClientFallbackFactory implements FallbackFactory<ApiCategoryClient> {
    @Override
    public ApiCategoryClient create(Throwable cause) {
        return new ApiCategoryClient() {
            @Override
            public ResponseVO<ListVO<ApiGoodsCategoryVO>> listL1() {
                log.error("获取分类失败了");
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }

            @Override
            public ResponseVO<ListVO<ApiGoodsCategoryVO>> listNavBarByL1(Integer l1id) {
                log.error("获取分类失败了");
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }

            @Override
            public ResponseVO<ApiCategoryCurrentVO> listNavBarByL2(Integer l2id) {
                log.error("获取分类失败了");
                return ResponseVO.ok(new ApiCategoryCurrentVO());
            }
        };
    }
}
