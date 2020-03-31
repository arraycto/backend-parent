package com.hyf.backend.coupon.template.admin.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ApiQueryCouponTemplateDTO extends QueryPageDTO {
    private String title;
    private Boolean isAvailable;
    private Boolean isExpired;
}
