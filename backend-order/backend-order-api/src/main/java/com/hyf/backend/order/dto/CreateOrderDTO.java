package com.hyf.backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    private Integer uid;
    private Integer cartId;
    private Integer couponId;
}
