package com.hyf.backend.admin.storage;

import java.io.InputStream;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface StorageStrategy {
    void upload(InputStream inputStream, Long contentLength, String contentType, String fileName) ;

    String generateUrl(String keyName);
}
