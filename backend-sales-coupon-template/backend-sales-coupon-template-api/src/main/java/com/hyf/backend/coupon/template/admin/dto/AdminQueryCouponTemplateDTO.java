package com.hyf.backend.coupon.template.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyf.backend.common.domain.QueryPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AdminQueryCouponTemplateDTO extends QueryPageDTO {
    private String title;
    private Boolean isAvailable;
    private Boolean isExpired;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
