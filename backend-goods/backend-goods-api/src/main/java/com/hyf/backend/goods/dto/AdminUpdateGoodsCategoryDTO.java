package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateGoodsCategoryDTO extends AdminCreateGoodsCategoryDTO implements Serializable {
    @NotNull
    private Integer id;
}
