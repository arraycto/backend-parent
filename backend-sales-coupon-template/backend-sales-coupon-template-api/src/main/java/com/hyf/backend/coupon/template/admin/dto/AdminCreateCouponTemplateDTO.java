package com.hyf.backend.coupon.template.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class AdminCreateCouponTemplateDTO {
    @NotNull(message = "标题不能为空")
    private String title;
    @NotNull(message = "logo不能为空")
    private String logo;
    @NotNull(message = "优惠券总数不能为空")
    @Min(value = 1)
    private Integer couponCount;
    @NotNull(message = "每人获得数量限制不能为空")
    private Integer eachGetLimitation;
    @NotNull(message = "优惠券折扣分类不能为空")
    private Integer discountCategory;
    private Integer manjianQuota;
    private Integer lijianQuota;
    private Integer zhekouQuota;
    private String dispatchUsers;
    private Integer discountBase;
    @NotNull(message = "有效期规则不能为空")
    private Integer expirationCode;
    private Integer expirationGap;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDeadline;
    private List<Integer> goodsTypeLimitation;
    private List<String> weight;

}
