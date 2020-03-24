package com.hyf.backend.common.vo;

import com.hyf.backend.common.domain.PageList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PageVO<T> extends ListVO<T> {
    private Integer pageSize;
    private Integer pageNo;
    private Long total;

    public PageVO(List<T> list, Integer pageSize, Integer pageNo, Long total) {
        super(list);
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public <I> PageVO(PageList<I> pageList, Function<? super I, ? extends T> mapper) {
        this(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
                pageList.getPageSize(),
                pageList.getPageNo(),
                pageList.getTotal());
    }
}
