package com.hyf.backend.user.service;

import com.hyf.backend.user.controller.vo.UserAuthVO;
import com.hyf.backend.user.dataobject.MallUser;
import com.hyf.backend.user.dataobject.MallUserExample;
import com.hyf.backend.user.dto.UserAuthDTO;
import com.hyf.backend.user.mapper.MallUserMapper;
import com.hyf.backend.utils.JWTUtils;
import com.hyf.backend.utils.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private MallUserMapper userTMapper;

    @Override
    public UserAuthVO authUser(UserAuthDTO authDTO) {
//        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name", authDTO.getUserName());
//        MoocBackendUserT moocBackendUserT = userTMapper.selectOne(queryWrapper);
        MallUserExample userExample = new MallUserExample();
        userExample.createCriteria().andUsernameEqualTo(authDTO.getUserName());
        List<MallUser> users = userTMapper.selectByExample(userExample);
        MallUser user = users.get(0);
        if (null == user) {
            throw new BizException(-5, "该用户不存在");
        }
        if (!(DigestUtils.md5DigestAsHex(authDTO.getUserPwd().getBytes(StandardCharsets.UTF_8)))
                .equals(user.getPassword())) {
            throw new BizException(-4, "密码错误");
        }
        UserAuthVO userAuthVO = new UserAuthVO();
        BeanUtils.copyProperties(user, userAuthVO);
        String token = JWTUtils.getInstance().getToken(userAuthVO.getId().toString());
        userAuthVO.setToken(token);
        userAuthVO.setNickName(user.getNickname());
        return userAuthVO;
    }
}
