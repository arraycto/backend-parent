package com.hyf.backend.goods.bo;

import com.hyf.backend.goods.dataobject.MallBrand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBrandBO implements Serializable {
    private Integer id;

    private String name;

    private String desc;

    private String picUrl;

    private Byte sortOrder;

    private BigDecimal floorPrice;

    public AdminBrandBO(MallBrand mallBrand) {
        BeanUtils.copyProperties(mallBrand, this);
    }
}
