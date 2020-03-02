package com.hyf.backend.coupon.template.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author huyufei
 * @since 2020-02-25
 */
//@JsonSerialize(using = ) 自定义Json序列化器，序列化该对象
public class SalesCouponTemplate extends Model<SalesCouponTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer available;

    private Integer expired;

    private String name;

    private String logo;

    /**
     * 线路本身的促销，发券，领券
     */
    private Integer type;

    private Integer productLine;

    private Integer couponCount;

    private LocalDateTime createTime;

    private Long adminUserId;

    private String templateKey;

    /**
     * 分发的人
     */
    private String dispatchUsers;

    /**
     * 规则json字符串存储，也可以新建一张表做关联
     */
    private String rule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getDispatchUsers() {
        return dispatchUsers;
    }

    public void setDispatchUsers(String dispatchUsers) {
        this.dispatchUsers = dispatchUsers;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "SalesCouponTemplate{" +
        ", id=" + id +
        ", available=" + available +
        ", expired=" + expired +
        ", name=" + name +
        ", logo=" + logo +
        ", type=" + type +
        ", productLine=" + productLine +
        ", couponCount=" + couponCount +
        ", createTime=" + createTime +
        ", adminUserId=" + adminUserId +
        ", templateKey=" + templateKey +
        ", dispatchUsers=" + dispatchUsers +
        ", rule=" + rule +
        "}";
    }
}
