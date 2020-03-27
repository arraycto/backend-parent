package com.hyf.backend.common.domain;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface DateTraceable {
    Date getCreateTime();
    Date getUpdateTime();
    void setCreateTime(Date createTime);
    void setUpdateTime(Date updateTime);
}
