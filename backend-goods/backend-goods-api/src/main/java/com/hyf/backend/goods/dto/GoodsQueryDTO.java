package com.hyf.backend.goods.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GoodsQueryDTO extends QueryPageDTO {
    private String goodsSn;
    private String name;
    private Integer categoryId;
}
