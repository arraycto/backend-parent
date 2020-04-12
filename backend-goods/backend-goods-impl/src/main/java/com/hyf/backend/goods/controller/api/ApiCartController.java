package com.hyf.backend.goods.controller.api;

import com.hyf.backend.common.annotation.CurrentUser;
import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.ApiCart;
import com.hyf.backend.goods.api.vo.ApiCartIndexVO;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiUserCartGoodsNumVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.ApiCartCheckedDTO;
import com.hyf.backend.goods.dto.ApiUserCartIndexDTO;
import com.hyf.backend.goods.dto.ClearUserCartDTO;
import com.hyf.backend.goods.service.MallCartService;
import com.hyf.backend.goods.service.MallGoodsService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiCartController implements ApiCart {

    @Autowired
    private MallCartService cartService;
    @Autowired
    private MallGoodsService goodsService;

    @Override
    public ResponseVO<ApiUserCartGoodsNumVO> countUserGoodsCount(@CurrentUser("uid") Integer uid) {
        return ResponseVO.ok(new ApiUserCartGoodsNumVO(cartService.userGoodsCount(uid)));
    }

    @Override
    public ResponseVO<ApiCartIndexVO> userCartIndex(ApiUserCartIndexDTO cartIndexDTO) {
        String s = ContextHolder.getCurrentContext()
                .get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isEmpty(s)) {
            throw new BizException(401, "必须要先登录服务");
        }
        uid = Integer.valueOf(s);
        ApiCartIndexVO indexVO = new ApiCartIndexVO();

        PageVO<ApiUserCartVO> apiUserCartVOPageVO = cartService.cartIndex(uid, cartIndexDTO);

        indexVO.setList(apiUserCartVOPageVO.getList());
        indexVO.setTotal(apiUserCartVOPageVO.getTotal());
        indexVO.setPageNo(apiUserCartVOPageVO.getPageNo());
        indexVO.setPageSize(apiUserCartVOPageVO.getPageSize());

        List<ApiUserCartVO> list = apiUserCartVOPageVO.getList();
        indexVO.setIsAllChecked(list.stream().filter((cartVO) -> !cartVO.getChecked()).count() == 0);

        Map<Integer, ApiUserCartVO> skuIdToCartVOMap = list.stream().collect(Collectors.toMap(ApiUserCartVO::getSkuId, vo -> vo));
        Set<Integer> skuIdList = skuIdToCartVOMap.keySet();
        List<ApiGoodsSkuVO> skuList = goodsService.findBySkuList(skuIdList);
        Map<Integer, ApiGoodsSkuVO> skuIdToSkuMap = skuList.stream().collect(Collectors.toMap(ApiGoodsSkuVO::getId, sku -> sku));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Integer, ApiUserCartVO> entry : skuIdToCartVOMap.entrySet()) {
            Integer key = entry.getKey();
            if (skuIdToSkuMap.containsKey(key)) {
                totalPrice = totalPrice.add(BigDecimal.valueOf(entry.getValue().getNumber()).multiply(skuIdToSkuMap.get(key).getPrice()));
            }
        }
        indexVO.setTotalPrice(totalPrice);
        return ResponseVO.ok(indexVO);
    }

    @Override
    public ResponseVO<ApiUserCartVO> addCart(ApiAddCartDTO apiAddCartDTO) {
        String s = ContextHolder.getCurrentContext()
                .get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Integer.valueOf(s);
        }
        return ResponseVO.ok(cartService.addCart(uid, apiAddCartDTO));
    }

    @Override
    public ResponseVO<Boolean> updateCartChecked(ApiCartCheckedDTO cartCheckedDTO) {
        String s = ContextHolder.getCurrentContext()
                .get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isEmpty(s)) {
            uid = Integer.valueOf(s);
        }
        return ResponseVO.ok(cartService.updateCartChecked(uid, cartCheckedDTO.setIsChecked(true)));
    }

    @Override
    public ResponseVO<Boolean> updateCartUnChecked(ApiCartCheckedDTO cartCheckedDTO) {
        String s = ContextHolder.getCurrentContext()
                .get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Integer.valueOf(s);
        }
        return ResponseVO.ok(cartService.updateCartChecked(uid, cartCheckedDTO.setIsChecked(false)));
    }

    @Override
    public ResponseVO<ListVO<ApiUserCartVO>> findCheckedGoods(Integer uid) {
        if (uid == null) {
            throw new BizException("必须要登录");
        }
        return ResponseVO.ok(new ListVO<>(cartService.findCheckedGoods(uid)));
    }

    @Override
    public ResponseVO<Integer> clearUserCart(@RequestBody ClearUserCartDTO clearUserCartDTO) {
        return ResponseVO.ok(cartService.clearUserCart(clearUserCartDTO.getUid()));
    }
}
