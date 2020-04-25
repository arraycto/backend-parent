package com.hyf.backend.goods.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiCategoryCurrentVO;
import com.hyf.backend.goods.api.vo.ApiGoodsCategoryVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/goods/category")
public interface ApiGoodsCategory {
    @GetMapping("/list-l1")
    ResponseVO<ListVO<ApiGoodsCategoryVO>> listL1();

    @GetMapping("/navbar-list-byl1")
    ResponseVO<ListVO<ApiGoodsCategoryVO>> listNavBarByL1(@RequestParam("l1id") Integer l1id);

    @GetMapping("/navbar-list-byl2")
    ResponseVO<ApiCategoryCurrentVO> listNavBarByL2(@RequestParam("l2id") Integer l2id);

    @GetMapping("/index")
    ResponseVO<Map<String, Object>> catalogIndex( Integer id);

    @GetMapping("/current")
    ResponseVO<Map<String, Object>> currentCatalog(Integer id);

}
