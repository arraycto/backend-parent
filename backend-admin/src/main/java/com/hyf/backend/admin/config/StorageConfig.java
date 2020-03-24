package com.hyf.backend.admin.config;

import com.hyf.backend.admin.storage.StorageProperties;
import com.hyf.backend.admin.storage.StorageService;
import com.hyf.backend.admin.storage.impl.AliyunStorageStrategy;
import com.hyf.backend.admin.storage.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public StorageService storageService() {
        String active = storageProperties.getActive();
        if ("aliyun".equals(active)) {
            return new StorageServiceImpl(new AliyunStorageStrategy(storageProperties.getAliyun()));
        }
        throw new RuntimeException("没有符合的存储策略");
    }

}
