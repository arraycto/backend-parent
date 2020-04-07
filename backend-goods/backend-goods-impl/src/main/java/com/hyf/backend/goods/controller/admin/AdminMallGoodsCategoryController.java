package com.hyf.backend.goods.controller.admin;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.AdminGoodsCategoryApi;
import com.hyf.backend.goods.admin.vo.AdminGoodsCategoryVO;
import com.hyf.backend.goods.bo.AdminGoodsCategoryBO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.goods.service.MallGoodsCategoryService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class AdminMallGoodsCategoryController implements AdminGoodsCategoryApi {

    @Autowired
    private MallGoodsCategoryService goodsCategoryService;

    @Override
    public ResponseVO<AdminGoodsCategoryVO> createGoodsCategory(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
        AdminGoodsCategoryBO goodsCategory = goodsCategoryService.createGoodsCategory(createGoodsCategoryDTO);
        AdminGoodsCategoryVO vo = new AdminGoodsCategoryVO();
        BeanUtils.copyProperties(goodsCategory, vo);
        return ResponseVO.ok(vo);
    }

    @Override
    public ResponseVO<AdminGoodsCategoryVO> updateGoodsCategory(AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO) {
        AdminGoodsCategoryBO categoryBO = goodsCategoryService.updateGoodsCategory(updateGoodsCategoryDTO);
        AdminGoodsCategoryVO vo = new AdminGoodsCategoryVO();
        BeanUtils.copyProperties(categoryBO, vo);
        return ResponseVO.ok(vo);
    }

    @Override
    public ResponseVO<PageVO<AdminGoodsCategoryVO>> findPageByQuery(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO) {
        PageListBO<AdminGoodsCategoryBO> pageByQuery = goodsCategoryService.findPageByQuery(queryGoodsCategoryDTO);
        List<AdminGoodsCategoryBO> list = pageByQuery.getList();
        List<AdminGoodsCategoryVO> voList = new ArrayList<>();
        for (AdminGoodsCategoryBO bo : list) {
            AdminGoodsCategoryVO vo = new AdminGoodsCategoryVO();
            BeanUtils.copyProperties(bo, vo);
            List<AdminGoodsCategoryVO> children = new ArrayList<>();
            for (AdminGoodsCategoryBO c : bo.getChildren()) {
                AdminGoodsCategoryVO vo1 = new AdminGoodsCategoryVO();
                BeanUtils.copyProperties(c, vo1);
                children.add(vo1);
            }
            vo.setChildren(children);
            voList.add(vo);
        }
        return ResponseVO.ok(new PageVO<>(voList, pageByQuery.getPageSize(), pageByQuery.getPageNo(), pageByQuery.getTotal()));
    }

    @Override
    public ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel1() {
        List<AdminGoodsCategoryBO> adminGoodsCategoryBOList = goodsCategoryService.listL1();
        List<AdminGoodsCategoryVO> voList = new ArrayList<>();
        for (AdminGoodsCategoryBO bo : adminGoodsCategoryBOList) {
            AdminGoodsCategoryVO vo = new AdminGoodsCategoryVO();
            BeanUtils.copyProperties(bo, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }

    @Override
    public ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel2() {
        return null;
    }


}
