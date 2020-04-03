package com.hyf.backend.springsource.service;

import com.hyf.backend.springsource.dto.TransferDTO;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserAccountService {

    void transfer(TransferDTO transferDTO);

    void testTransaction();
}
