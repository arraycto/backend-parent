package com.hyf.backend.coupon.template.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCouponSettlementVO {
    private List<ApiUserCouponVO> userAvailableCouponList;

    private BigDecimal totalGoodsPrice;
    /**
     * 优惠后的金额
     */
    private BigDecimal cost;
    /**
     * 优惠了多少钱，减免最多的
     */
    private BigDecimal discountPrice;
    /**
     * 是否使结算生效, 即核销
     */
    private Boolean employ;

    private Integer maxDiscountCouponId;
}
