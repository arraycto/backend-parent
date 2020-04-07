package com.hyf.backend.goods.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsDetailVO {
    private AdminGoodsVO goods;
    private List<AdminGoodsSpecVO> specifications;
    private List<AdminGoodsSkuVO> skus;
    private List<Integer> categoryIds;
    private List<AdminGoodsAttrVO> attributes;
}
