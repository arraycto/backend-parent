package com.hyf.backend.coupon.template.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Elvis on 2020/4/4
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private BigDecimal price;
    private Integer count;
    private Integer type;

}
