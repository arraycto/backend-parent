package com.hyf.backend.goods.bo;

import com.hyf.backend.goods.dataobject.MallGoods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsBO {
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

    public AdminGoodsBO(MallGoods mallGoods) {
        BeanUtils.copyProperties(mallGoods, this);
    }
}
