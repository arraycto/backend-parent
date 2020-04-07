package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBrandCreateDTO implements Serializable {

    private String name;

    private String desc;

    private String picUrl;

    private Byte sortOrder;

    private BigDecimal floorPrice;
}
