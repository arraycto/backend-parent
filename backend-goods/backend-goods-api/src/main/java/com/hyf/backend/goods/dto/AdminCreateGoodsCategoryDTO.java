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
public class AdminCreateGoodsCategoryDTO implements Serializable {
    @NotNull
    private String name;
    private String keywords;
    private String desc;
    private Integer pid;
    private String iconUrl;
    private String picUrl;
    private String level;
    private Integer sortOrder;
}
