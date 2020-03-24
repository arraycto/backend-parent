package com.hyf.backend.admin.controller.vo;

import com.hyf.backend.admin.storage.StorageBO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class StorageVO {
    private Integer id;
    private String key;
    private String name;
    private String type;
    private Long size;
    private String url;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;

    public StorageVO(StorageBO storageBO) {
        BeanUtils.copyProperties(storageBO, this);
    }
}
