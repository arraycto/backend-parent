package com.hyf.backend.goods.controller.api;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.ApiGoods;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.api.vo.ApiSpecVO;
import com.hyf.backend.goods.api.vo.ApiSpecValueVO;
import com.hyf.backend.goods.bo.GoodsBO;
import com.hyf.backend.goods.dataobject.MallBrand;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsAttr;
import com.hyf.backend.goods.dataobject.MallGoodsSku;
import com.hyf.backend.goods.dataobject.MallGoodsSpec;
import com.hyf.backend.goods.dto.ApiSkuIdListQueryDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.goods.service.MallBrandService;
import com.hyf.backend.goods.service.MallGoodsService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiGoodsController implements ApiGoods {

    @Autowired
    private MallGoodsService goodsService;
    @Autowired
    private MallBrandService brandService;

    @Override
    public ResponseVO<ListVO<ApiGoodsVO>> listByNew() {
        List<MallGoods> mallGoods = goodsService.listByNew();
        List<ApiGoodsVO> voList = new ArrayList<>();
        for (MallGoods goods : mallGoods) {
            ApiGoodsVO vo = new ApiGoodsVO();
            BeanUtils.copyProperties(goods, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @Override
    public ResponseVO<ListVO<ApiGoodsVO>> listByHot() {
        List<MallGoods> mallGoods = goodsService.listByHot();
        List<ApiGoodsVO> voList = new ArrayList<>();
        for (MallGoods goods : mallGoods) {
            ApiGoodsVO vo = new ApiGoodsVO();
            BeanUtils.copyProperties(goods, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @Override
    public ResponseVO<PageVO<ApiGoodsVO>> listByCategory(@RequestBody GoodsQueryDTO goodsQueryDTO) {
        PageListBO<GoodsBO> pageByQuerySimple = goodsService.findPageByQuerySimple(goodsQueryDTO);
        List<ApiGoodsVO> voList = new ArrayList<>();
        for (GoodsBO bo : pageByQuerySimple.getList()) {
            ApiGoodsVO vo = new ApiGoodsVO();
            BeanUtils.copyProperties(bo, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new PageVO<>(voList, pageByQuerySimple.getPageSize(), pageByQuerySimple.getPageNo(), pageByQuerySimple.getTotal()));
    }

    @Override
    public ResponseVO<Map<String, Object>> goodsDetail(Integer id) {
        Map<String, Object> resultMap = new HashMap<>();

        MallGoods mallGoods = goodsService.findById(id);
        resultMap.put("info", mallGoods);
        List<MallGoodsSpec> specList = goodsService.findSpecByGoodsId(id);
        Map<String, List<MallGoodsSpec>> specNameToMap = specList.stream().collect(Collectors.groupingBy(MallGoodsSpec::getSpecification));
        List<ApiSpecValueVO> specValueList = new ArrayList<>(specNameToMap.size());
        for (Map.Entry<String, List<MallGoodsSpec>> entry : specNameToMap.entrySet()) {
            ApiSpecValueVO valueVO = new ApiSpecValueVO();
            valueVO.setName(entry.getKey());
            List<ApiSpecVO> specVOList = new ArrayList<>(entry.getValue().size());
            for (MallGoodsSpec spec : entry.getValue()) {
                ApiSpecVO vo = new ApiSpecVO();
                BeanUtils.copyProperties(spec, vo);
                specVOList.add(vo);
            }
            valueVO.setValueList(specVOList);
            specValueList.add(valueVO);
        }
        resultMap.put("specificationList", specValueList);

        //skuList
        List<MallGoodsSku> skuByGoodsId = goodsService.findSkuByGoodsId(id);
        resultMap.put("productList", skuByGoodsId);
        List<MallGoodsAttr> attrByGoodsId = goodsService.findAttrByGoodsId(id);
        resultMap.put("attribute", attrByGoodsId);
        MallBrand mallBrand = brandService.findById(mallGoods.getBrandId());
        resultMap.put("brand", mallBrand);
        return ResponseVO.ok(resultMap);
    }

    @Override
    public ResponseVO<ListVO<ApiGoodsSkuVO>> findBySkuList(ApiSkuIdListQueryDTO apiSkuIdListQueryDTO) {
        return ResponseVO.ok(new ListVO<>(goodsService.findBySkuList(apiSkuIdListQueryDTO.getSkuIdList())));
    }
}
