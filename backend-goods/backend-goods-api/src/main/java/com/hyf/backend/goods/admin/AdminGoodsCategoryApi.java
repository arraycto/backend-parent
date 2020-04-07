package com.hyf.backend.goods.admin;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsCategoryVO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/goods/category")
public interface AdminGoodsCategoryApi {
    @PostMapping("/create")
    ResponseVO<AdminGoodsCategoryVO> createGoodsCategory(@RequestBody AdminCreateGoodsCategoryDTO createGoodsCategoryDTO);

    @PostMapping("/list")
    ResponseVO<PageVO<AdminGoodsCategoryVO>> findPageByQuery(@RequestBody AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO);

    @PostMapping("/update")
    ResponseVO<AdminGoodsCategoryVO> updateGoodsCategory(@RequestBody AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO);

    @PostMapping("/list-level1")
    ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel1();

    @PostMapping("/list-level2")
    ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel2();

}
