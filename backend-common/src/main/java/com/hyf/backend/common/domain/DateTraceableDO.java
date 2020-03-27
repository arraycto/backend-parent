package com.hyf.backend.common.domain;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class DateTraceableDO implements DateTraceable {
    private Date createTime;
    private Date updateTime;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
