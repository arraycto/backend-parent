package com.hyf.backend.portal.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.dto.ApiSkuIdListQueryDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.portal.feign.ApiGoodsClient;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class ApiGoodsClientFallbackFactory implements FallbackFactory<ApiGoodsClient> {
    @Override
    public ApiGoodsClient create(Throwable cause) {
        return new ApiGoodsClient() {
            @Override
            public ResponseVO<ListVO<ApiGoodsVO>> listByNew() {
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }

            @Override
            public ResponseVO<ListVO<ApiGoodsVO>> listByHot() {
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }

            @Override
            public ResponseVO<PageVO<ApiGoodsVO>> listByCategory(GoodsQueryDTO queryGoodsDTO) {
                return ResponseVO.ok(new PageVO<>());
            }

            @Override
            public ResponseVO<Map<String, Object>> goodsDetail(Integer id) {
                return ResponseVO.ok(new HashMap<>());
            }

            @Override
            public ResponseVO<ListVO<ApiGoodsSkuVO>> findBySkuList(ApiSkuIdListQueryDTO apiSkuIdListQueryDTO) {
                return null;
            }
        };
    }
}
