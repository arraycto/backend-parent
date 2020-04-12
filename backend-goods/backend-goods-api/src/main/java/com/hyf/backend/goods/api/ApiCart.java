package com.hyf.backend.goods.api;

import com.hyf.backend.common.annotation.CurrentUser;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.vo.ApiCartIndexVO;
import com.hyf.backend.goods.api.vo.ApiUserCartGoodsNumVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.ApiCartCheckedDTO;
import com.hyf.backend.goods.dto.ApiUserCartIndexDTO;
import com.hyf.backend.goods.dto.ClearUserCartDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/cart")
public interface ApiCart {
    @GetMapping("/goods-count")
    ResponseVO<ApiUserCartGoodsNumVO> countUserGoodsCount(@CurrentUser("uid") Integer uid);

    @GetMapping("/index")
    ResponseVO<ApiCartIndexVO> userCartIndex(ApiUserCartIndexDTO cartIndexDTO);

    @PostMapping("/add")
    ResponseVO<ApiUserCartVO> addCart(@RequestBody ApiAddCartDTO apiAddCartDTO);

    @PostMapping("/set-checked")
    ResponseVO<Boolean> updateCartChecked(@RequestBody ApiCartCheckedDTO cartCheckedDTO);

    @PostMapping("/set-unchecked")
    ResponseVO<Boolean> updateCartUnChecked(@RequestBody ApiCartCheckedDTO cartCheckedDTO);

    @GetMapping("/find-checked-goods")
    ResponseVO<ListVO<ApiUserCartVO>> findCheckedGoods(@CurrentUser("uid") Integer uid);

    @PostMapping("/clear-user-checked-goods")
    ResponseVO<Integer> clearUserCart(@RequestBody ClearUserCartDTO clearUserCartDTO);
}
