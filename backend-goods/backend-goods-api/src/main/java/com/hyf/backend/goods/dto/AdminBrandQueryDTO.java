package com.hyf.backend.goods.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminBrandQueryDTO extends QueryPageDTO {


    private String name;

    private String desc;

}
