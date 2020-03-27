package com.hyf.backend.common.domain;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class AuthorTraceableDO implements AuthorTraceable {
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
}
