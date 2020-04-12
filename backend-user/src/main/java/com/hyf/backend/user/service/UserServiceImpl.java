package com.hyf.backend.user.service;

import com.hyf.backend.utils.exception.BizException;
import org.springframework.stereotype.Service;

/**
 * @Author: Elvis on 2020/2/14
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserServiceImpl implements UserService {
//    @Autowired
//    private MoocBackendUserTMapper backendUserTMapper;

    @Override
    public String checkUserLogin(String username, String password) throws BizException {
//        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name", "huyufei");
//        List<MoocBackendUserT> list = backendUserTMapper.selectList(queryWrapper);
//        if (list.size() == 0) {
//            throw new BizException(404, "用户名输入有误");
//        }
//        MoocBackendUserT moocBackendUserT = list.stream().findFirst().get();
//        if (!((Digester.createMd5Digest().getStringDigest(password)).equals(moocBackendUserT.getUserPwd()))) {
//            throw new BizException(500, "密码输入错误");
//        }
//        return moocBackendUserT.getUuid() + "";
        return null;
    }
}
