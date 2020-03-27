package com.hyf.backend.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class IntegerIdReqDto {
    @NotNull(message = "id不能为空")
    private Integer id;
}
