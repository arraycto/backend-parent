package com.hyf.backend.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSkuIdListQueryDTO {
    private List<Integer> skuIdList;
}
