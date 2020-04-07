package com.hyf.backend.goods.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.bo.AdminBrandBO;
import com.hyf.backend.goods.dto.AdminBrandCreateDTO;
import com.hyf.backend.goods.dto.AdminBrandQueryDTO;
import com.hyf.backend.goods.dto.AdminBrandUpdateDTO;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallBrandService {

    PageListBO<AdminBrandBO> queryByPage(AdminBrandQueryDTO brandQueryDTO);


    AdminBrandBO createBrand(AdminBrandCreateDTO brandCreateDTO);


    AdminBrandBO updateBrand(AdminBrandUpdateDTO brandUpdateDTO);
}
