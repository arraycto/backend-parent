package com.hyf.backend.coupon.template.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.feign.ApiCartClient;
import com.hyf.backend.goods.api.vo.ApiCartIndexVO;
import com.hyf.backend.goods.api.vo.ApiUserCartGoodsNumVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.ApiCartCheckedDTO;
import com.hyf.backend.goods.dto.ApiUserCartIndexDTO;
import com.hyf.backend.goods.dto.ClearUserCartDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class CartFallbackFactory implements FallbackFactory<ApiCartClient> {
    @Override
    public ApiCartClient create(Throwable cause) {
        return new ApiCartClient() {
            @Override
            public ResponseVO<ApiUserCartGoodsNumVO> countUserGoodsCount(Integer uid) {
                return null;
            }

            @Override
            public ResponseVO<ApiCartIndexVO> userCartIndex(ApiUserCartIndexDTO cartIndexDTO) {
                return null;
            }

            @Override
            public ResponseVO<ApiUserCartVO> addCart(ApiAddCartDTO apiAddCartDTO) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateCartChecked(ApiCartCheckedDTO cartCheckedDTO) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateCartUnChecked(ApiCartCheckedDTO cartCheckedDTO) {
                return null;
            }

            @Override
            public ResponseVO<ListVO<ApiUserCartVO>> findCheckedGoods(Integer uid) {
                return null;
            }

            @Override
            public ResponseVO<Integer> clearUserCart(ClearUserCartDTO clearUserCartDTO) {
                return null;
            }

        };
    }
}
