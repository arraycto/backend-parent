package com.hyf.backend.goods.api.vo;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApiCartIndexVO extends PageVO<ApiUserCartVO>{

    private BigDecimal totalPrice;
    private Boolean isAllChecked;


}
