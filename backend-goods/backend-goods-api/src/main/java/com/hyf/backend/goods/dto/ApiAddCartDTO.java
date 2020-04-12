package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiAddCartDTO {
    private Integer uid;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer skuId;
    private String goodsSn;
    private String goodsName;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Min(1)
    private Integer number;
    private List<String> specifications;
    private String picUrl;
}
