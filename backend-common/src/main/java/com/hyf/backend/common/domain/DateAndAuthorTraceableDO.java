package com.hyf.backend.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DateAndAuthorTraceableDO implements DateAndAuthorTraceable {
    private Date createTime;
    private Date updateTime;
    private Integer createUser;
    private Integer updateUser;

    @Override
    public Integer getCreateUser() {
        return createUser;
    }

    @Override
    public Integer getUpdateUser() {
        return updateUser;
    }

    @Override
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Override
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

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
