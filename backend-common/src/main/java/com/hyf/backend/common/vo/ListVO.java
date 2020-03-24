package com.hyf.backend.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Elvis on 2020/3/24
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ListVO<T> {
    private List<T> list;
    public ListVO(List<T> list) {
        this.list = list;
    }
}
