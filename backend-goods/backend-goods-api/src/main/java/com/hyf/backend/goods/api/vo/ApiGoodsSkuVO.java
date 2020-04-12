package com.hyf.backend.goods.api.vo;

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
public class ApiGoodsSkuVO {
    private Integer id;

    private Integer goodsId;

    private List specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private ApiGoodsVO goods;
}
