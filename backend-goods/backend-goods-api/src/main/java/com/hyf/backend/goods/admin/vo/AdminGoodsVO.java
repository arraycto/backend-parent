package com.hyf.backend.goods.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGoodsVO implements Serializable {
    private Integer id;

    private String goodsSn;

    private String name;

    private Integer categoryId;

    private Integer brandId;

    private List<String> gallery;

    private String keywords;

    private String brief;

    private Boolean isOnSale;

    private Short sortOrder;

    private String picUrl;

    private String shareUrl;

    private Boolean isNew;

    private Boolean isHot;

    private String unit;

    private BigDecimal counterPrice;

    private BigDecimal retailPrice;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private String detail;

}
