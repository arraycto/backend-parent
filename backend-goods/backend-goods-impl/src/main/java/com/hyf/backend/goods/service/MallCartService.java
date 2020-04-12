package com.hyf.backend.goods.service;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.vo.ApiCartIndexVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.ApiCartCheckedDTO;
import com.hyf.backend.goods.dto.ApiUserCartIndexDTO;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallCartService {
    int userGoodsCount(Integer userId);

    PageVO<ApiUserCartVO> cartIndex(Integer uid, ApiUserCartIndexDTO indexDTO);

    ApiUserCartVO addCart(Integer uid, ApiAddCartDTO apiAddCartDTO);

    Boolean updateCartChecked(Integer uid, ApiCartCheckedDTO cartCheckedDTO);

    List<ApiUserCartVO> findCheckedGoods(Integer uid);

    Integer clearUserCart(Integer uid);
}
