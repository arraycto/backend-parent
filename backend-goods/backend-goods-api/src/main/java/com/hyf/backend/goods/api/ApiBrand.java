package com.hyf.backend.goods.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiBrandVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/goods/brand")
public interface ApiBrand {
    @GetMapping("/list-all")
    ResponseVO<ListVO<ApiBrandVO>> listAll();
}
