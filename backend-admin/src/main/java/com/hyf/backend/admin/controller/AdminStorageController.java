package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.controller.vo.StorageVO;
import com.hyf.backend.admin.storage.StorageService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/storage")
public class AdminStorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/create")
    public ResponseVO<StorageVO> createStorage(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseVO.ok(new StorageVO(storageService.upload(file)));
    }
}
