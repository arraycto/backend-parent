package com.hyf.backend.goods.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsDetailVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsVO;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/goods")
public interface AdminGoodsApi {

    @PostMapping("/create")
    ResponseVO<AdminGoodsVO> createGoods(@RequestBody AdminGoodsAggregationTO adminGoodsAggregationCreateDTO);

    @GetMapping("/list-brand-category")
    ResponseVO<Map<String, Object>> listBrandAndCategory();

    @GetMapping("/goods-detail")
    ResponseVO<AdminGoodsDetailVO> goodsDetail(@RequestParam("id") Integer id);

    @PostMapping("/list")
    ResponseVO<PageVO<AdminGoodsVO>> findPageByQuery(@RequestBody GoodsQueryDTO adminGoodsQueryDTO);

    @PostMapping("/update")
    ResponseVO<AdminGoodsVO> updateGoods(@RequestBody AdminGoodsAggregationTO goodsAggregationTO);


}
