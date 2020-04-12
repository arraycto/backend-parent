package com.hyf.backend.coupon.template.dataobject;

import com.hyf.backend.common.domain.DateAndAuthorTraceableDO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import java.util.Date;
import java.util.List;

public class CouponTemplateDO extends DateAndAuthorTraceableDO {
    private Long id;

    private Boolean isAvailable;

    private Boolean isExpired;

    private String title;

    private String logo;

    private CouponDiscountCategoryEnum discountCategory;

    private Integer couponCount;

    private String templateKey;

    private String dispatchUsers;

    private CouponTemplateExpirationEnum expirationCode;

    private Integer expirationGap;

    private Date expirationDeadline;

    private Integer manjianQuota;

    private Integer lijianQuota;

    private Integer zhekouQuota;

    private Integer discountBase;

    private Integer eachGetLimitation;

    private List goodsTypeLimitation;

    private List weight;

    private String tag;

    private String desc;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public CouponDiscountCategoryEnum getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(CouponDiscountCategoryEnum discountCategory) {
        this.discountCategory = discountCategory;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey == null ? null : templateKey.trim();
    }

    public String getDispatchUsers() {
        return dispatchUsers;
    }

    public void setDispatchUsers(String dispatchUsers) {
        this.dispatchUsers = dispatchUsers == null ? null : dispatchUsers.trim();
    }

    public CouponTemplateExpirationEnum getExpirationCode() {
        return expirationCode;
    }

    public void setExpirationCode(CouponTemplateExpirationEnum expirationCode) {
        this.expirationCode = expirationCode;
    }

    public Integer getExpirationGap() {
        return expirationGap;
    }

    public void setExpirationGap(Integer expirationGap) {
        this.expirationGap = expirationGap;
    }

    public Date getExpirationDeadline() {
        return expirationDeadline;
    }

    public void setExpirationDeadline(Date expirationDeadline) {
        this.expirationDeadline = expirationDeadline;
    }

    public Integer getManjianQuota() {
        return manjianQuota;
    }

    public void setManjianQuota(Integer manjianQuota) {
        this.manjianQuota = manjianQuota;
    }

    public Integer getLijianQuota() {
        return lijianQuota;
    }

    public void setLijianQuota(Integer lijianQuota) {
        this.lijianQuota = lijianQuota;
    }

    public Integer getZhekouQuota() {
        return zhekouQuota;
    }

    public void setZhekouQuota(Integer zhekouQuota) {
        this.zhekouQuota = zhekouQuota;
    }

    public Integer getDiscountBase() {
        return discountBase;
    }

    public void setDiscountBase(Integer discountBase) {
        this.discountBase = discountBase;
    }

    public Integer getEachGetLimitation() {
        return eachGetLimitation;
    }

    public void setEachGetLimitation(Integer eachGetLimitation) {
        this.eachGetLimitation = eachGetLimitation;
    }

    public List getGoodsTypeLimitation() {
        return goodsTypeLimitation;
    }

    public void setGoodsTypeLimitation(List goodsTypeLimitation) {
        this.goodsTypeLimitation = goodsTypeLimitation;
    }

    public List getWeight() {
        return weight;
    }

    public void setWeight(List weight) {
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}