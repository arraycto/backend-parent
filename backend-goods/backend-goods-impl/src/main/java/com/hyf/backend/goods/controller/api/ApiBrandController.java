package com.hyf.backend.goods.controller.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.ApiBrand;
import com.hyf.backend.goods.api.vo.ApiBrandVO;
import com.hyf.backend.goods.dataobject.MallBrand;
import com.hyf.backend.goods.service.MallBrandService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiBrandController implements ApiBrand {
    @Autowired
    private MallBrandService brandService;

    @Override
    public ResponseVO<ListVO<ApiBrandVO>> listAll() {
        List<MallBrand> mallBrands = brandService.listAll();
        List<ApiBrandVO> voList = new ArrayList<>();
        for (MallBrand brand : mallBrands) {
            ApiBrandVO vo = new ApiBrandVO();
            BeanUtils.copyProperties(brand, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }
}
