package com.hyf.backend.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.hyf.backend.admin.admin.dao.AdminUserMapper;
import com.hyf.backend.admin.admin.dataobject.AdminUserDO;
import com.hyf.backend.admin.storage.StorageProperties;
import com.hyf.backend.admin.util.Permission;
import com.hyf.backend.admin.util.PermissionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AdminApplicationTests {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private StorageProperties storageProperties;

    @Test
    public void test() {
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectListByPage("haha", "add_time", "asc", 0, 10);
        System.out.println(adminUserDOS);
    }

    @Test
    public void testListPermissions() {
        List<Permission> permissions = PermissionUtil.listSysPermission(applicationContext, "com.hyf.backend.admin.controller");
        log.info("permissions: {}", permissions);
    }

    @Test
    public void testGenRandomStr() {
        String random = RandomStringUtils.randomAlphabetic(10);
        System.err.println(random);
    }
    @Test
    public void testAliyunOssUpload() {
        OSS ossClient = new OSSClientBuilder().build(storageProperties.getAliyun().getEndpoint(), storageProperties.getAliyun().getAccessKeyId(), storageProperties.getAliyun().getAccessKeySecret());
        PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getAliyun().getBucketName(), 1234567+"", new File("/Users/huyufei/Downloads/timg.jpeg"));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }
}
