package com.hyf.backend.goods.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/9
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCategoryCurrentVO {
    private List<ApiGoodsCategoryVO> brotherList;
        private ApiGoodsCategoryVO current;
}
