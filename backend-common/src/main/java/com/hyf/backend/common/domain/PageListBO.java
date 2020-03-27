package com.hyf.backend.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageListBO<T> {

    private List<T> list;
    private Long total;
    public Integer pageNo;
    private Integer pageSize;

    public PageListBO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public <I> PageListBO(PageListBO<I> pageList, Function<? super I, ? extends T> mapper) {
        this.list = pageList.getList().stream().map(mapper).collect(Collectors.toList());
        this.pageSize = pageList.getPageSize();
        this.pageNo = pageList.getPageNo();
        this.total = pageList.getTotal();
    }

    public PageListBO(List<T> list, Page page, long totalCnt) {
        this.list = list;
        this.pageSize = page.getPageSize();
        this.pageNo = page.getPageNo();
        this.total = totalCnt;
    }

}
