package com.hyf.backend.goods.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminQueryGoodsCategoryDTO extends QueryPageDTO {

    private String name;

}
