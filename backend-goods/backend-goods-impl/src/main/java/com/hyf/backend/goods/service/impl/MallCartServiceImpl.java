package com.hyf.backend.goods.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dataobject.MallCart;
import com.hyf.backend.goods.dataobject.MallCartExample;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsSku;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.ApiCartCheckedDTO;
import com.hyf.backend.goods.dto.ApiUserCartIndexDTO;
import com.hyf.backend.goods.mapper.MallCartMapper;
import com.hyf.backend.goods.service.MallCartService;
import com.hyf.backend.goods.service.MallGoodsService;
import com.hyf.backend.utils.exception.BizException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/10
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class MallCartServiceImpl implements MallCartService {
    @Autowired
    private MallCartMapper cartMapper;
    @Autowired
    private MallGoodsService goodsService;

    @Override
    public int userGoodsCount(Integer uid) {
        if (uid == null) return 0;
        MallCartExample example = new MallCartExample();
        example.createCriteria().andUserIdEqualTo(uid).andIsDeletedEqualTo(false);
        return cartMapper.countByExample(example).intValue();
    }

    @Override
    public PageVO<ApiUserCartVO> cartIndex(Integer uid, ApiUserCartIndexDTO cartIndexDTO) {
        MallCartExample cartExample = new MallCartExample();
        cartExample.createCriteria().andUserIdEqualTo(uid).andIsDeletedEqualTo(false);
        PageListBO<MallCart> mallCartPageListBO =
                MapperHelper.selectPageByExample(cartMapper, cartExample, cartIndexDTO.toPage());
        List<ApiUserCartVO> voList = new ArrayList<>(mallCartPageListBO.getList().size());
        for (MallCart cart : mallCartPageListBO.getList()) {
            ApiUserCartVO cartVO = new ApiUserCartVO();
            BeanUtils.copyProperties(cart, cartVO);
            voList.add(cartVO);
        }
        return new PageVO<>(voList, mallCartPageListBO.getPageSize(), mallCartPageListBO.getPageNo(), mallCartPageListBO.getTotal());
    }

    @Override
    public ApiUserCartVO addCart(Integer uid, ApiAddCartDTO apiAddCartDTO) {
        MallCartExample example = new MallCartExample();
        example.createCriteria().andUserIdEqualTo(uid).andGoodsIdEqualTo(apiAddCartDTO.getGoodsId())
                .andSkuIdEqualTo(apiAddCartDTO.getSkuId()).andIsDeletedEqualTo(false);
        List<MallCart> mallCarts = cartMapper.selectByExample(example);
        ApiUserCartVO cartVO = new ApiUserCartVO();
        Integer skuId = apiAddCartDTO.getSkuId();
        MallGoodsSku goodsSku = goodsService.findSkuById(skuId);
        if (CollectionUtils.isNotEmpty(mallCarts)) {
            assert mallCarts.size() == 1;
            MallCart cart = mallCarts.get(0);
            if (cart.getNumber() + apiAddCartDTO.getNumber() > goodsSku.getNumber()) {
                throw new BizException("库存不足");
            }
            MallCart toUpdate = new MallCart();
            toUpdate.setId(cart.getId());
            toUpdate.setNumber((short) (cart.getNumber() + apiAddCartDTO.getNumber()));
            cartMapper.updateByPrimaryKeySelective(toUpdate);
            BeanUtils.copyProperties(cartMapper.selectByPrimaryKey(cart.getId()), cartVO);
            return cartVO;
        } else {
            //判断库存
            if (goodsSku.getNumber() - apiAddCartDTO.getNumber() < 0) {
                throw new BizException("库存不足");
            }
            MallGoods mallGoods = goodsService.findById(apiAddCartDTO.getGoodsId());
            MallCart toCreate = new MallCart();
            BeanUtils.copyProperties(apiAddCartDTO, toCreate);
            toCreate.setChecked(true);
            toCreate.setUserId(uid);
            if (StringUtils.isNotEmpty(mallGoods.getPicUrl())) {
                toCreate.setPicUrl(mallGoods.getPicUrl());
            } else {
                toCreate.setPicUrl(goodsSku.getUrl());
            }
            toCreate.setNumber((short) apiAddCartDTO.getNumber().intValue());
            toCreate.setSpecifications(goodsSku.getSpecifications());
            toCreate.setGoodsName(mallGoods.getName());
            toCreate.setGoodsSn(mallGoods.getGoodsSn());
            toCreate.setPrice(goodsSku.getPrice());
            cartMapper.insertSelective(toCreate);
            BeanUtils.copyProperties(toCreate, cartVO);
            return cartVO;
        }

    }

    @Override
    public Boolean updateCartChecked(Integer uid, ApiCartCheckedDTO cartCheckedDTO) {
        MallCartExample cartExample = new MallCartExample();
        if (CollectionUtils.isEmpty(cartCheckedDTO.getCartIds())) {
            return true;
        }
        cartExample.createCriteria().andIdIn(cartCheckedDTO.getCartIds())
                .andUserIdEqualTo(uid);
        MallCart toUpdate = new MallCart();
        toUpdate.setChecked(cartCheckedDTO.getIsChecked());
        Integer integer = cartMapper.updateByExampleSelective(toUpdate, cartExample);
        return integer > 0;
    }

    @Override
    public List<ApiUserCartVO> findCheckedGoods(Integer uid) {
        MallCartExample example = new MallCartExample();
        example.createCriteria().andUserIdEqualTo(uid).andIsDeletedEqualTo(false);
        List<MallCart> mallCarts = cartMapper.selectByExample(example);
        List<ApiUserCartVO> voList = new ArrayList<>();

        for (MallCart cart : mallCarts) {
            ApiUserCartVO vo = new ApiUserCartVO();
            BeanUtils.copyProperties(cart, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public Integer clearUserCart(Integer uid) {
        MallCartExample mallCartExample = new MallCartExample();
        mallCartExample.createCriteria().andUserIdEqualTo(uid)
                .andCheckedEqualTo(true);
        MallCart toUpdate = new MallCart();
        toUpdate.setIsDeleted(true);
        return cartMapper.updateByExampleSelective(toUpdate, mallCartExample);
    }
}
