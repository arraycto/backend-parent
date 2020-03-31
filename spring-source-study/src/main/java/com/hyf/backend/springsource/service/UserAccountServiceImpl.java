package com.hyf.backend.springsource.service;

import com.hyf.backend.springsource.dto.TransferDTO;
import com.hyf.backend.springsource.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    @IsTryAgain
    @Override
    public void transfer(TransferDTO transferDTO) {
        updateAccount(transferDTO.getFromUserId(), transferDTO.getAmount(), "OUT");
        updateAccount(transferDTO.getToUserId(), transferDTO.getAmount(), "IN");
    }

    private void updateAccount(Integer userId, Integer amount, String type) {
        if (type.equals("OUT")) {
            UserAccount userAccount = userAccountMapper.selectByUserId(userId);
            int i = userAccountMapper.updateDecrAccountCAS(userId, userAccount.getBalance() - amount, userAccount.getVersion());
            if(i == 0) {
                System.err.println("增加更新失败了");
                throw new TryAgainException("更新失败，进入重试");
            }
        } else {
            UserAccount userAccount = userAccountMapper.selectByUserId(userId);
            int i= userAccountMapper.updateIncrAccountCAS(userId, userAccount.getBalance() + amount, userAccount.getVersion());
            if(i == 0) {
                System.err.println("减少更新失败了");
                throw new TryAgainException("更新失败，进入重试");
            }
        }

    }
}
