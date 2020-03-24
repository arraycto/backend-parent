package com.hyf.backend.admin.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@ConfigurationProperties(prefix = "backend.storage")
@Data
public class StorageProperties {
    private String active;
    private Aliyun aliyun;

    @Data
    public static class Aliyun {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }
}
