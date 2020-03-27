package com.hyf.backend.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String sort;
    private String orderBy;

    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public int getLimit() {
        return this.pageSize;
    }

    public QueryPageDTO(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public QueryPageDTO(int pageSize, int pageNo, String orderBy) {
        this(pageSize, pageNo);
        this.orderBy = orderBy;
    }

    public QueryPageDTO(int pageSize, int pageNo, String orderBy, String sort) {
        this(pageSize, pageNo, orderBy);
        this.sort = sort;
    }

    public Page toPage() {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setOrderBy(orderBy);
        page.setSort(sort);
        return page;
    }



}
