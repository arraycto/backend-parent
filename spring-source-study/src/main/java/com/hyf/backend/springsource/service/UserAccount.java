package com.hyf.backend.springsource.service;

import lombok.Data;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class UserAccount {
    private Integer id;
    private Integer userId;
    private Integer balance;
    private Integer version;
}
