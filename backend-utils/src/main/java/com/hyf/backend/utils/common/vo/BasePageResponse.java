package com.hyf.backend.utils.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Elvis on 2020/2/15
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageResponse<T> extends ResponseVO<T> {

    private long totalSize;
    private long totalPages;
    private long pageSize;
    private long nowPage;

    public BasePageResponse(IPage<T> page) {
        super((T) page.getRecords());
        this.totalPages = page.getPages();
        this.totalSize = page.getSize();
        this.pageSize = page.getSize();
        this.nowPage = page.getCurrent();
    }
}
