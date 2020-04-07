package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsAggregationTO {
    private AdminGoodsCreateDTO goods;
    private List<AdminGoodsSpecCreateDTO> specifications;
    private List<AdminGoodsSkuCreateDTO> skus;
    private List<AdminGoodsAttrCreateDTO> attributes;

}
