package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsSkuCreateDTO implements Serializable {
    private Integer id;

    private Integer goodsId;

    private List<String> specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

}
