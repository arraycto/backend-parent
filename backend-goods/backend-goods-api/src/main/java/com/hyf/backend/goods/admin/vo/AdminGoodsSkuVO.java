package com.hyf.backend.goods.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsSkuVO {
    private Integer id;

    private Integer goodsId;

    private List specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;
}
