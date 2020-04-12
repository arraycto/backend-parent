package com.hyf.backend.goods.controller.admin;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.AdminGoodsApi;
import com.hyf.backend.goods.admin.vo.AdminGoodsDetailVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsVO;
import com.hyf.backend.goods.bo.GoodsBO;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.goods.service.MallGoodsService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class AdminMallGoodsController implements AdminGoodsApi {

    @Autowired
    private MallGoodsService mallGoodsService;

    @Override
    public ResponseVO<AdminGoodsVO> createGoods(AdminGoodsAggregationTO adminGoodsAggregationCreateDTO) {
        GoodsBO goods = mallGoodsService.createGoods(adminGoodsAggregationCreateDTO);
        AdminGoodsVO adminGoodsVO = new AdminGoodsVO();
        BeanUtils.copyProperties(goods, adminGoodsVO);
        return ResponseVO.ok(adminGoodsVO);
    }

    @Override
    public ResponseVO<Map<String, Object>> listBrandAndCategory() {
        Map<String, Object> stringObjectMap = mallGoodsService.listBrandAndCategory();
        return ResponseVO.ok(stringObjectMap);
    }

    @Override
    public ResponseVO<AdminGoodsDetailVO> goodsDetail(Integer id) {
        return ResponseVO.ok(mallGoodsService.goodsDetail(id));
    }

    @Override
    public ResponseVO<PageVO<AdminGoodsVO>> findPageByQuery(GoodsQueryDTO adminGoodsQueryDTO) {
        PageListBO<GoodsBO> pageByQuery =
                mallGoodsService.findPageByQuery(adminGoodsQueryDTO);
        List<AdminGoodsVO> voList = new ArrayList<>(pageByQuery.getList().size());
        for (GoodsBO bo : pageByQuery.getList()) {
            AdminGoodsVO vo = new AdminGoodsVO();
            BeanUtils.copyProperties(bo, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new PageVO<>(voList, pageByQuery.getPageSize(), pageByQuery.getPageNo(), pageByQuery.getTotal()));
    }

    @Override
    public ResponseVO<AdminGoodsVO> updateGoods(AdminGoodsAggregationTO goodsAggregationTO) {
        GoodsBO adminGoodsBO = mallGoodsService.updateGoods(goodsAggregationTO);
        AdminGoodsVO vo = new AdminGoodsVO();
        BeanUtils.copyProperties(adminGoodsBO, vo);
        return ResponseVO.ok(vo);
    }

}
