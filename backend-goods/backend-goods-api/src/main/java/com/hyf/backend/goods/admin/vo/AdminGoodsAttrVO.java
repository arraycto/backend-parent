package com.hyf.backend.goods.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsAttrVO {
    private Integer id;

    private Integer goodsId;

    private String attribute;

    private String value;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;
}
