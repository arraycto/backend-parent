package com.hyf.backend.coupon.template.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCouponSettlementDTO {
    private Integer uid;
    private Integer cartId;
    private Integer couponId;
}
