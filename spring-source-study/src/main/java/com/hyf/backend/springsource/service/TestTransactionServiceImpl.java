package com.hyf.backend.springsource.service;

import com.hyf.backend.springsource.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Elvis on 2020/4/2
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class TestTransactionServiceImpl implements TestTransactionService {
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Transactional
    @Override
    public void test1() {
        int i = userAccountMapper.updateIncrAccount(1, 100);
        try{
            userAccountService.testTransaction();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test2() {

    }
}
