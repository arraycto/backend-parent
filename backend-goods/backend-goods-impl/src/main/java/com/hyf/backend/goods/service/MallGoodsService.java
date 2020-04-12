package com.hyf.backend.goods.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.goods.admin.vo.AdminGoodsDetailVO;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.bo.GoodsBO;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsAttr;
import com.hyf.backend.goods.dataobject.MallGoodsSku;
import com.hyf.backend.goods.dataobject.MallGoodsSpec;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsService {
    GoodsBO createGoods(AdminGoodsAggregationTO adminGoodsAggregationTO);

    PageListBO<GoodsBO> findPageByQuery(GoodsQueryDTO goodsQueryDTO);

    GoodsBO updateGoods(AdminGoodsAggregationTO  adminGoodsAggregationTO);

    Map<String, Object> listBrandAndCategory();

    AdminGoodsDetailVO goodsDetail(Integer id);


    List<MallGoods> listByNew();

    List<MallGoods> listByHot();

    PageListBO<GoodsBO> findPageByQuerySimple(GoodsQueryDTO goodsQueryDTO);

    void goodsDetailPortal(Integer id);

    MallGoods findById(Integer id);

    List<MallGoodsSpec> findSpecByGoodsId(Integer goodsId);

    List<MallGoodsSku> findSkuByGoodsId(Integer id);

    List<MallGoodsAttr> findAttrByGoodsId(Integer id);

    List<ApiGoodsSkuVO> findBySkuList(Collection<Integer> skuIdList);

    ApiUserCartVO addCart(Integer uid, ApiAddCartDTO apiAddCartDTO);

    MallGoodsSku findSkuById(Integer skuId);

}
