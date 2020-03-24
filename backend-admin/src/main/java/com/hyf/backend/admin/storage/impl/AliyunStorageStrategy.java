package com.hyf.backend.admin.storage.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.hyf.backend.admin.storage.StorageProperties;
import com.hyf.backend.admin.storage.StorageStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
public class AliyunStorageStrategy implements StorageStrategy {
    private StorageProperties.Aliyun aliyun;

    public AliyunStorageStrategy(StorageProperties.Aliyun aliyun) {
        this.aliyun = aliyun;
    }

    private String getBaseUrl() {
        return "https://" + aliyun.getBucketName() + "." + aliyun.getEndpoint() + "/";
    }

    @Override
    public String generateUrl(String keyName) {
        return getBaseUrl() + keyName;
    }

    @Override
    public void upload(InputStream inputStream, Long contentLength, String contentType, String fileName) {
        try {
            // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            objectMetadata.setContentType(contentType);
            // 对象键（Key）是对象在存储桶中的唯一标识。
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyun.getBucketName(), fileName, inputStream, objectMetadata);
            PutObjectResult putObjectResult = getOSSClient().putObject(putObjectRequest);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    private OSS getOSSClient() {
        return new OSSClientBuilder().build(aliyun.getEndpoint(), aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
    }
}
