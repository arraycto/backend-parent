package com.hyf.backend.goods.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/9
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSpecVO {
    private Integer id;

    private Integer goodsId;

    private String specification;

    private String value;

    private String picUrl;
}
