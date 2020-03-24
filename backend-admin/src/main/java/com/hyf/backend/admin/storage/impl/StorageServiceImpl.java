package com.hyf.backend.admin.storage.impl;

import com.hyf.backend.admin.storage.StorageBO;
import com.hyf.backend.admin.storage.StorageService;
import com.hyf.backend.admin.storage.StorageStrategy;
import com.hyf.backend.admin.storage.dao.StorageMapper;
import com.hyf.backend.admin.storage.dataobject.StorageDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;
    private StorageStrategy storageStrategy;

    public StorageServiceImpl(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    @Override
    public StorageBO upload(MultipartFile file) throws IOException {
        String key = generateFileKey(Objects.requireNonNull(file.getOriginalFilename()));
        storageStrategy.upload(file.getInputStream(), file.getSize(), file.getContentType(), key);
        String url = generateUrl(key);
        StorageDO storageDO = new StorageDO()
                .setAddTime(new Date())
                .setDeleted(false)
                .setKey(key)
                .setName(file.getOriginalFilename())
                .setSize(file.getSize())
                .setType(file.getContentType())
                .setUpdateTime(new Date())
                .setUrl(url);
        storageMapper.insert(storageDO);
        return new StorageBO(storageDO);
    }

    private String generateUrl(String key) {
        return storageStrategy.generateUrl(key);
    }

    private String generateFileKey(String originalFilename) {
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(0, i);
        StorageDO storageDO = null;
        String key = null;
        do {
            String s = RandomStringUtils.randomAlphabetic(10);
            key = s + suffix;
            storageDO = storageMapper.selectByKey(key);
        } while (storageDO != null);
        return key;
    }
}
