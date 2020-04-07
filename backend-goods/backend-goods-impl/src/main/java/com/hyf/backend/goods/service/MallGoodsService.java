package com.hyf.backend.goods.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.goods.admin.vo.AdminGoodsDetailVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsVO;
import com.hyf.backend.goods.bo.AdminGoodsBO;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.AdminGoodsQueryDTO;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsService {
    AdminGoodsBO createGoods(AdminGoodsAggregationTO adminGoodsAggregationTO);

    PageListBO<AdminGoodsBO> findPageByQuery(AdminGoodsQueryDTO goodsQueryDTO);

    AdminGoodsBO updateGoods(AdminGoodsAggregationTO  adminGoodsAggregationTO);

    Map<String, Object> listBrandAndCategory();

    AdminGoodsDetailVO goodsDetail(Integer id);


}
