package com.hyf.backend.coupon.template.api.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Elvis on 2020/4/3
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiListByStatusDTO extends QueryPageDTO implements Serializable {
    @NotNull
    private Integer status;
}
