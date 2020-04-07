package com.hyf.backend.goods.dataobject;

import com.hyf.backend.common.domain.DateAndAuthorTraceableDO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MallGoodsSku extends DateAndAuthorTraceableDO {
    private Integer id;

    private Integer goodsId;

    private List specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List specifications) {
        this.specifications = specifications;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}