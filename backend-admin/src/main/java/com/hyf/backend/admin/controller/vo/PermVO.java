package com.hyf.backend.admin.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/23
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class PermVO {
    private String id;
    private Integer roleId;
    private String permission;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;
    private String label;
    private String api;
    private List<PermVO> children;
}

