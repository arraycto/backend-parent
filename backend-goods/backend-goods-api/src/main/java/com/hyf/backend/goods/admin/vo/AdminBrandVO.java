package com.hyf.backend.goods.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBrandVO {
    private Integer id;

    private String name;

    private String desc;

    private String picUrl;

    private Byte sortOrder;

    private BigDecimal floorPrice;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;
}
