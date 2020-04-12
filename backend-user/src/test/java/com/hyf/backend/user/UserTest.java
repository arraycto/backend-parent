package com.hyf.backend.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyf.backend.user.entity.MoocBackendUserT;
//import com.hyf.backend.user.mapper.MoocBackendUserTMapper;
import com.hyf.backend.utils.secure.Digester;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Elvis on 2020/2/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

public class UserTest extends BackendUserApplicationTests {
//    @Resource
//    private MoocBackendUserTMapper moocBackendUserTMapper;

    @Test
    public void add() {
//        for (int i = 0; i < 20; ++i) {
        MoocBackendUserT moocBackendUser = new MoocBackendUserT();
        moocBackendUser.setUserName("huyufei");
        moocBackendUser.setUserPhone("18206089675");
        moocBackendUser.setUserPwd(Digester.createMd5Digest().getStringDigest("12345"));
//        moocBackendUserTMapper.insert(moocBackendUser);
//        }

    }

    @Test
    public void select() {
//        MoocBackendUserT moocBackendUserT = moocBackendUserTMapper.selectById(2);
//        System.out.println(moocBackendUserT);

//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("user_name", "huyufei14");
//        List<MoocBackendUserT> moocBackendUserTS = moocBackendUserTMapper.selectList(queryWrapper);
//        System.out.println(moocBackendUserTS);
    }

    @Test
    public void selectByPage() {
        Page<MoocBackendUserT> page = new Page<>(1, 10);
//        IPage<MoocBackendUserT> moocBackendUserTIPage = moocBackendUserTMapper.selectPage(page, null);

    }


    @Test
    public void update() {
        MoocBackendUserT moocBackendUser = new MoocBackendUserT();
        moocBackendUser.setUserName("胡禹飞");
        moocBackendUser.setUuid(2);
        moocBackendUser.setUserPwd("gdhuyufei");
        moocBackendUser.setUserPhone("18206089675");
//        moocBackendUserTMapper.updateById(moocBackendUser);
//        moocBackendUser.setUserName("胡禹飞");

    }
}
