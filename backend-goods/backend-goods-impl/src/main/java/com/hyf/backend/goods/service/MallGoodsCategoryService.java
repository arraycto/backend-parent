package com.hyf.backend.goods.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.goods.bo.AdminGoodsCategoryBO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallGoodsCategoryService {

    AdminGoodsCategoryBO createGoodsCategory(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO);

    AdminGoodsCategoryBO updateGoodsCategory(AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO);

    PageListBO<AdminGoodsCategoryBO> findPageByQuery(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO);

    List<AdminGoodsCategoryBO> listL1();

    List<AdminGoodsCategoryBO> listL2();
}
