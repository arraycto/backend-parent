package com.hyf.backend.coupon.template.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class ApiQueryIdsDTO {
    @NotNull(message = "ids集合不能为空")
    @NotEmpty(message = "ids集合不能为空")
    private List<Long> ids;
}
