package com.hyf.backend.portal.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiBrandVO;
import com.hyf.backend.portal.feign.ApiBrandClient;
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
public class ApiBrandClientFallbackFactory implements FallbackFactory<ApiBrandClient> {
    @Override
    public ApiBrandClient create(Throwable cause) {
        return new ApiBrandClient() {
            @Override
            public ResponseVO<ListVO<ApiBrandVO>> listAll() {
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }
        };
    }
}
