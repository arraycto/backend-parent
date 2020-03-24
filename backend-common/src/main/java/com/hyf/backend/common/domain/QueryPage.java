package com.hyf.backend.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
public class QueryPage {
    private Integer pageNo;
    private Integer pageSize;
    private String sort;
    private String orderBy;

    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public int getLimit() {
        return this.pageSize;
    }

    public QueryPage(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public QueryPage(int pageSize, int pageNo, String orderBy) {
        this(pageSize, pageNo);
        this.orderBy = orderBy;
    }

    public QueryPage(int pageSize, int pageNo, String orderBy, String sort) {
        this(pageSize, pageNo, orderBy);
        this.sort = sort;
    }

}
