package com.hyf.backend.goods.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminBrandVO;
import com.hyf.backend.goods.dto.AdminBrandCreateDTO;
import com.hyf.backend.goods.dto.AdminBrandQueryDTO;
import com.hyf.backend.goods.dto.AdminBrandUpdateDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/goods/brand")
public interface AdminBrandApi {

    @PostMapping("/create")
    ResponseVO<AdminBrandVO> createBrand(@RequestBody AdminBrandCreateDTO brandCreateDTO);

    @PostMapping("/list")
    ResponseVO<PageVO<AdminBrandVO>> listBrand(@RequestBody AdminBrandQueryDTO brandQueryDTO);

    @PostMapping("/update")
    ResponseVO<AdminBrandVO> updateBrand(@RequestBody AdminBrandUpdateDTO brandUpdateDTO);
}
