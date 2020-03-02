package com.hyf.backend.distribution.coupon.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author huyufei
 * @since 2020-02-29
 */
public class SalesCoupon extends Model<SalesCoupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long templateId;

    private Long userId;

    private String couponCode;

    /**
     * 获得的时间
     */
    private LocalDateTime getTime;

    /**
     * 已使用的，未使用的，过期的，直接设置状态，不用设置时间，省去了检索优惠券时的逻辑判断
     */
    private Integer status;

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
        this.couponCode = couponCode;
    }

    public LocalDateTime getGetTime() {
        return getTime;
    }

    public void setGetTime(LocalDateTime getTime) {
        this.getTime = getTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "SalesCoupon{" +
        ", id=" + id +
        ", templateId=" + templateId +
        ", userId=" + userId +
        ", couponCode=" + couponCode +
        ", getTime=" + getTime +
        ", status=" + status +
        "}";
    }
}
