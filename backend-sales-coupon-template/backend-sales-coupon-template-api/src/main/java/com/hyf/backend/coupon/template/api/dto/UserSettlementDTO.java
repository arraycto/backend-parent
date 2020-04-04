package com.hyf.backend.coupon.template.api.dto;

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
public class UserSettlementDTO implements Serializable {
    private List<Long> userCouponIdList;
    private List<ProductDTO> productDTOList;

    /**
     * 是否使结算生效, 即核销
     */
    private Boolean employ;
}
