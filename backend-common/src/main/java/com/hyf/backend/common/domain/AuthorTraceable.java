package com.hyf.backend.common.domain;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface AuthorTraceable {
    Integer getCreateUser();
    Integer getUpdateUser();
    void setCreateUser(Integer createUser);
    void setUpdateUser(Integer updateUser);
}
