package com.hyf.backend.coupon.template.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettlementBO implements Serializable {
    private List<UserCouponBO> userCouponBO;
    private BigDecimal cost;
    /**
     * 是否使结算生效, 即核销
     */
    private Boolean employ;
    private BigDecimal totalGoodsPrice;
}
