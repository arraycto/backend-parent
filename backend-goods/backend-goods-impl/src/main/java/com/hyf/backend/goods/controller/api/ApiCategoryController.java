package com.hyf.backend.goods.controller.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.ApiGoodsCategory;
import com.hyf.backend.goods.api.vo.ApiCategoryCurrentVO;
import com.hyf.backend.goods.api.vo.ApiGoodsCategoryVO;
import com.hyf.backend.goods.dataobject.MallGoodsCategory;
import com.hyf.backend.goods.service.MallGoodsCategoryService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiCategoryController implements ApiGoodsCategory {
    @Autowired
    private MallGoodsCategoryService categoryService;

    @Override
    public ResponseVO<ListVO<ApiGoodsCategoryVO>> listL1() {
        List<MallGoodsCategory> mallGoodsCategories = categoryService.listL1Simple();
        List<ApiGoodsCategoryVO> voList = new ArrayList<>();
        for (MallGoodsCategory category : mallGoodsCategories) {
            ApiGoodsCategoryVO vo = new ApiGoodsCategoryVO();
            BeanUtils.copyProperties(category, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @Override
    public ResponseVO<ListVO<ApiGoodsCategoryVO>> listNavBarByL1(@RequestParam("l1id") Integer l1id) {
        List<MallGoodsCategory> mallGoodsCategories = categoryService.listNaBarByL1(l1id);
        List<ApiGoodsCategoryVO> voList = new ArrayList<>();
        for (MallGoodsCategory mallGoodsCategory : mallGoodsCategories) {
            ApiGoodsCategoryVO vo = new ApiGoodsCategoryVO();
            BeanUtils.copyProperties(mallGoodsCategory, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @Override
    public ResponseVO<ApiCategoryCurrentVO> listNavBarByL2(@RequestParam("l2id") Integer l2id) {
        List<MallGoodsCategory> mallGoodsCategories = categoryService.listNaBarByL2(l2id);
        MallGoodsCategory current = categoryService.findById(l2id);
        ApiGoodsCategoryVO currentVO = new ApiGoodsCategoryVO();
        BeanUtils.copyProperties(current, currentVO);
        List<ApiGoodsCategoryVO> voList = new ArrayList<>();
        ApiCategoryCurrentVO voRes = new ApiCategoryCurrentVO();

        for (MallGoodsCategory mallGoodsCategory : mallGoodsCategories) {
            ApiGoodsCategoryVO vo = new ApiGoodsCategoryVO();
            BeanUtils.copyProperties(mallGoodsCategory, vo);
            voList.add(vo);
        }
        voRes.setBrotherList(voList);
        voRes.setCurrent(currentVO);
        return ResponseVO.ok(voRes);
    }
}
