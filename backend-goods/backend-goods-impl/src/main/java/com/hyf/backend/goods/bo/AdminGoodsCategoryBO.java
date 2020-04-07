package com.hyf.backend.goods.bo;

import com.hyf.backend.goods.dataobject.MallGoodsCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class AdminGoodsCategoryBO {
    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private Integer pid;

    private String iconUrl;

    private String picUrl;

    private String level;

    private Byte sortOrder;

    private Date addTime;

    private Date updateTime;

    private Boolean isDeleted;

    private List<AdminGoodsCategoryBO> children;

    public AdminGoodsCategoryBO(MallGoodsCategory mallGoodsCategory) {
        BeanUtils.copyProperties(mallGoodsCategory, this);
    }
}
