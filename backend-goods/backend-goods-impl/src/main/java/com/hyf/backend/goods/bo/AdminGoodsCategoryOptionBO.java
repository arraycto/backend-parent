package com.hyf.backend.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGoodsCategoryOptionBO {
    private Integer value;
    private String label;
    private List<AdminGoodsCategoryOptionBO> children;
}
