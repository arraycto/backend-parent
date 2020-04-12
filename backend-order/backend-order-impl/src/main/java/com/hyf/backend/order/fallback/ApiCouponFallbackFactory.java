package com.hyf.backend.order.fallback;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.api.dto.ApiCartIdReq;
import com.hyf.backend.coupon.template.api.dto.ApiCouponSettlementDTO;
import com.hyf.backend.coupon.template.api.dto.ApiListByStatusDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.coupon.template.api.vo.ApiUserCouponVO;
import com.hyf.backend.order.feign.ApiCouponClient;
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
public class ApiCouponFallbackFactory implements FallbackFactory<ApiCouponClient> {
    @Override
    public ApiCouponClient create(Throwable cause) {
        return new ApiCouponClient() {

            @Override
            public ResponseVO<ListVO<ApiCouponTemplateVO>> listAvailableTemplate(Long uid) {
                log.error("获取优惠券列表失败了");
                return ResponseVO.ok(new ListVO<>(new ArrayList<>()));
            }

            @Override
            public ResponseVO<PageVO<ApiUserCouponVO>> listByStatus(ApiListByStatusDTO apiListByStatusDTO) {
                return null;
            }

            @Override
            public ResponseVO<ApiCouponSettlementVO> settlementCheckedGoods(ApiCouponSettlementDTO settlementDTO) {
                log.error("优惠券结算失败了, ", cause);
                return ResponseVO.ok(new ApiCouponSettlementVO());
            }

            @Override
            public ResponseVO<ListVO<ApiUserCouponVO>> listMatchCartGoods(ApiCartIdReq cartIdReq) {
                log.error("优惠券结算失败了, ", cause);
                return ResponseVO.ok(new ListVO<>());
            }
        };
    }
}
