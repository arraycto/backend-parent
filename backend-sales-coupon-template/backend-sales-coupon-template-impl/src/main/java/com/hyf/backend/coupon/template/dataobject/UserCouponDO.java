package com.hyf.backend.coupon.template.dataobject;

import com.hyf.backend.common.domain.DateAndAuthorTraceableDO;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import java.util.Date;

public class UserCouponDO extends DateAndAuthorTraceableDO {
    private Long id;

    private Long templateId;

    private Long userId;

    private String couponCode;

    private Date getTime;

    private UserCouponStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public UserCouponStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserCouponStatusEnum status) {
        this.status = status;
    }
}