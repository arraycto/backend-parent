package com.hyf.backend.coupon.template.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_ABSENT)
public class ApiCouponTemplateVO {
    private Long id;

    private Boolean isAvailable;

    private Boolean isExpired;

    private String title;

    private String logo;

    /**
     * 优惠券分类，折扣、立减、满减
     */
    private Integer discountCategory;

    private Integer couponCount;

    private Integer createUserId;

    private String templateKey;

    /**
     * 分发的人
     */
    private String dispatchUsers;

    /**
     * 有效期规则 0:固定有效期 1:变动的，从领取之日计算
     */
    private Integer expirationCode;

    /**
     * 变动的，从领取之日开始算
     */
    private Integer expirationGap;

    /**
     * 失效日期，两类规则都有效
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDeadline;

    /**
     * 折扣表示折扣了多少如85折，满减表示满减了多少比如100，立减表示立减了多少
     */
    private Integer manjianQuota;

    private Integer lijianQuota;

    private Integer zhekouQuota;

    /**
     * 满减基准
     */
    private Integer discountBase;

    /**
     * 每人领取优惠券限制
     */
    private Integer eachGetLimitation;

    /**
     * 商品类型限制范围
     */
    private List<Integer> goodsTypeLimitation;

    /**
     * 可以和哪些优惠券一起叠加使用，同一类优惠券不能叠加使用
     */
    private List<String> weight;

    private String tag;

    private String desc;

    private Date createTime;

    private Date updateTime;


}
