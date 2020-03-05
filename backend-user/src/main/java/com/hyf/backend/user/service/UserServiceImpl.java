package com.hyf.backend.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyf.backend.user.entity.MoocBackendUserT;
import com.hyf.backend.user.mapper.MoocBackendUserTMapper;
import com.hyf.backend.utils.exception.BizException;
import com.hyf.backend.utils.secure.Digester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Elvis on 2020/2/14
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MoocBackendUserTMapper backendUserTMapper;

    @Override
    public String checkUserLogin(String username, String password) throws BizException {
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "huyufei");
        List<MoocBackendUserT> list = backendUserTMapper.selectList(queryWrapper);
        if (list.size() == 0) {
            throw new BizException(404, "用户名输入有误");
        }
        MoocBackendUserT moocBackendUserT = list.stream().findFirst().get();
        if (!((Digester.createMd5Digest().getStringDigest(password)).equals(moocBackendUserT.getUserPwd()))) {
            throw new BizException(500, "密码输入错误");
        }
        return moocBackendUserT.getUuid() + "";
    }
}
