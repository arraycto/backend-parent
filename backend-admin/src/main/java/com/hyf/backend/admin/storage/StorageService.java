package com.hyf.backend.admin.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface StorageService {

    StorageBO upload(MultipartFile file) throws IOException;


}
