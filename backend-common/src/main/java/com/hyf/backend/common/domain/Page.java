package com.hyf.backend.common.domain;

import lombok.Data;

/**
 * @Author: Elvis on 2020/3/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class Page {
    public int pageSize;
    public int pageNo;
    public String orderBy;
    private String sort;
}
