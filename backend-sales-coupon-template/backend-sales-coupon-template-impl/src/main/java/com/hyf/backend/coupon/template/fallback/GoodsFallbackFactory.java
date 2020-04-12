package com.hyf.backend.coupon.template.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.feign.ApiGoodsClient;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.dto.ApiSkuIdListQueryDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class GoodsFallbackFactory implements FallbackFactory<ApiGoodsClient> {
    @Override
    public ApiGoodsClient create(Throwable cause) {
        return new ApiGoodsClient() {
            @Override
            public ResponseVO<ListVO<ApiGoodsVO>> listByNew() {
                return null;
            }

            @Override
            public ResponseVO<ListVO<ApiGoodsVO>> listByHot() {
                return null;
            }

            @Override
            public ResponseVO<PageVO<ApiGoodsVO>> listByCategory(GoodsQueryDTO queryGoodsDTO) {
                return null;
            }

            @Override
            public ResponseVO<Map<String, Object>> goodsDetail(Integer id) {
                return null;
            }

            @Override
            public ResponseVO<ListVO<ApiGoodsSkuVO>> findBySkuList(ApiSkuIdListQueryDTO apiSkuIdListQueryDTO) {
                log.error("调用商品服务出错啦,error: {}", cause.getMessage());
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }
        };
    }
}
