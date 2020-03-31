package com.hyf.backend.coupon.template.api.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiQueryByIdAndTypeDTO extends QueryPageDTO {
    private Integer status;
}
