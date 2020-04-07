package com.hyf.backend.goods.controller.admin;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.AdminBrandApi;
import com.hyf.backend.goods.admin.vo.AdminBrandVO;
import com.hyf.backend.goods.bo.AdminBrandBO;
import com.hyf.backend.goods.dto.AdminBrandCreateDTO;
import com.hyf.backend.goods.dto.AdminBrandQueryDTO;
import com.hyf.backend.goods.dto.AdminBrandUpdateDTO;
import com.hyf.backend.goods.service.MallBrandService;
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
public class AdminMallBrandController implements AdminBrandApi {
    @Autowired
    private MallBrandService mallBrandService;

    @Override
    public ResponseVO<AdminBrandVO> createBrand(AdminBrandCreateDTO brandCreateDTO) {
        AdminBrandBO brand = mallBrandService.createBrand(brandCreateDTO);
        AdminBrandVO vo = new AdminBrandVO();
        BeanUtils.copyProperties(brand, vo);
        return ResponseVO.ok(vo);
    }

    @Override
    public ResponseVO<PageVO<AdminBrandVO>> listBrand(AdminBrandQueryDTO brandQueryDTO) {
        PageListBO<AdminBrandBO> adminBrandBOPageListBO = mallBrandService.queryByPage(brandQueryDTO);
        List<AdminBrandVO> voList = new ArrayList<>();
        for (AdminBrandBO bo : adminBrandBOPageListBO.getList()) {
            AdminBrandVO vo = new AdminBrandVO();
            BeanUtils.copyProperties(bo, vo);
            voList.add(vo);
        }
        PageVO<AdminBrandVO> adminBrandVOPageVO = new PageVO<>(voList, adminBrandBOPageListBO.getPageSize(), adminBrandBOPageListBO.getPageNo(), adminBrandBOPageListBO.getTotal());
        return ResponseVO.ok(adminBrandVOPageVO);
    }

    @Override
    public ResponseVO<AdminBrandVO> updateBrand(AdminBrandUpdateDTO brandUpdateDTO) {
        AdminBrandBO adminBrandBO = mallBrandService.updateBrand(brandUpdateDTO);
        AdminBrandVO vo = new AdminBrandVO();
        BeanUtils.copyProperties(adminBrandBO, vo);
        return ResponseVO.ok(vo);
    }
}
