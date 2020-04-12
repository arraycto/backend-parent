package com.hyf.backend.goods.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiSkuIdListQueryDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/goods")
public interface ApiGoods {
    @GetMapping("/list-by-new")
    ResponseVO<ListVO<ApiGoodsVO>> listByNew();

    @GetMapping("/list-by-hot")
    ResponseVO<ListVO<ApiGoodsVO>> listByHot();

    @PostMapping("/list-by-category")
    ResponseVO<PageVO<ApiGoodsVO>> listByCategory(@RequestBody GoodsQueryDTO queryGoodsDTO);

    @GetMapping("/detail")
    ResponseVO<Map<String, Object>> goodsDetail(@RequestParam("id") Integer id);

    @PostMapping("/find-by-ids")
    ResponseVO<ListVO<ApiGoodsSkuVO>> findBySkuList(@RequestBody ApiSkuIdListQueryDTO apiSkuIdListQueryDTO);


}
