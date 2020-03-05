package com.hyf.backend.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyf.backend.user.controller.vo.UserAuthVO;
import com.hyf.backend.user.dto.UserAuthDTO;
import com.hyf.backend.user.entity.MoocBackendUserT;
import com.hyf.backend.user.mapper.MoocBackendUserTMapper;
import com.hyf.backend.utils.JWTUtils;
import com.hyf.backend.utils.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private MoocBackendUserTMapper userTMapper;

    @Override
    public UserAuthVO authUser(UserAuthDTO authDTO) {
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", authDTO.getUserName());
        MoocBackendUserT moocBackendUserT = userTMapper.selectOne(queryWrapper);
        if (null == moocBackendUserT) {
            throw new BizException(-5, "该用户不存在");
        }
        if (!(DigestUtils.md5DigestAsHex(authDTO.getUserPwd().getBytes(StandardCharsets.UTF_8)))
                .equals(moocBackendUserT.getUserPwd())) {
            throw new BizException(-4, "密码错误");
        }
        UserAuthVO userAuthVO = new UserAuthVO();
        BeanUtils.copyProperties(moocBackendUserT, userAuthVO);
        String token = JWTUtils.getInstance().getToken(userAuthVO.getUuid().toString());
        userAuthVO.setToken(token);
        return userAuthVO;
    }
}
