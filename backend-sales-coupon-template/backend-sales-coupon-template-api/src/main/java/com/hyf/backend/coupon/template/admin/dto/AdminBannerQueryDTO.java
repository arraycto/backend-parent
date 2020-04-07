package com.hyf.backend.coupon.template.admin.dto;

import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminBannerQueryDTO extends QueryPageDTO {
    private String name;

    private Date startTime;

    private Date endTime;

    private Boolean isEnabled;
}
