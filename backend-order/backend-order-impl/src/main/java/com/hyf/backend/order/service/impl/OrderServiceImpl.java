package com.hyf.backend.order.service.impl;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.dto.ApiCouponSettlementDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ClearUserCartDTO;
import com.hyf.backend.order.constant.OrderConstantEnum;
import com.hyf.backend.order.dataobject.MallOrder;
import com.hyf.backend.order.dataobject.MallOrderExample;
import com.hyf.backend.order.dataobject.MallOrderGoods;
import com.hyf.backend.order.dto.CreateOrderDTO;
import com.hyf.backend.order.feign.ApiCartClient;
import com.hyf.backend.order.feign.ApiCouponClient;
import com.hyf.backend.order.feign.ApiGoodsClient;
import com.hyf.backend.order.mapper.MallOrderGoodsMapper;
import com.hyf.backend.order.mapper.MallOrderMapper;
import com.hyf.backend.order.service.OrderService;
import com.hyf.backend.order.vo.ApiOrderGoodsVO;
import com.hyf.backend.order.vo.OrderVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private MallOrderMapper orderMapper;
    @Autowired
    private ApiCartClient cartClient;
    @Autowired
    private ApiGoodsClient goodsClient;
    @Autowired
    private ApiCouponClient couponClient;
    @Autowired
    private MallOrderGoodsMapper orderGoodsMapper;

    @Override
    public OrderVO submitOrder(CreateOrderDTO createOrderDTO) {
        MallOrder order = new MallOrder();
        order.setOrderStatus(OrderConstantEnum.CREATE.getCode().shortValue());
        if (createOrderDTO.getCartId() == null) {
            ResponseVO<ListVO<ApiUserCartVO>> checkedGoods = cartClient.findCheckedGoods(createOrderDTO.getUid());
            List<ApiUserCartVO> cartVOList = checkedGoods.getData().getList();
            if (createOrderDTO.getCouponId() != null) {
                Integer couponId = createOrderDTO.getCouponId();
                ApiCouponSettlementDTO settlementDTO = new ApiCouponSettlementDTO();
                settlementDTO.setUid(createOrderDTO.getUid());
                settlementDTO.setCartId(createOrderDTO.getCartId());
                settlementDTO.setCouponId(createOrderDTO.getCouponId());
                ResponseVO<ApiCouponSettlementVO> apiCouponSettlementVOResponseVO = couponClient.settlementCheckedGoods(settlementDTO);
                ApiCouponSettlementVO settlementVO = apiCouponSettlementVOResponseVO.getData();
                BigDecimal totalGoodsPrice = settlementVO.getTotalGoodsPrice();
                order.setGoodsPrice(totalGoodsPrice);

                order.setActualPrice(settlementVO.getCost());
                order.setFreightPrice(BigDecimal.valueOf(0));
                order.setGrouponPrice(BigDecimal.valueOf(0));
                order.setIntegralPrice(BigDecimal.valueOf(0));
                order.setConsignee("fdf");
                order.setAddress("默认地址");

                order.setCouponPrice(settlementVO.getDiscountPrice());
                order.setOrderPrice(settlementVO.getCost());
                order.setFreightPrice(BigDecimal.valueOf(88));
                order.setMobile("18206089675");
                order.setUserId(createOrderDTO.getUid());
                order.setOrderSn(RandomStringUtils.random(20, "abcdefghijklmnopqrstuvwxyz"));
                orderMapper.insertSelective(order);
                Integer orderId = order.getId();
                //插入订单表项
                for (ApiUserCartVO cartVO : cartVOList) {
                    MallOrderGoods toCreate = new MallOrderGoods();
                    toCreate.setGoodsId(cartVO.getGoodsId());
                    toCreate.setGoodsName(cartVO.getGoodsName());
                    toCreate.setGoodsSn(cartVO.getGoodsSn());
                    toCreate.setNumber(cartVO.getNumber());
                    toCreate.setOrderId(orderId);
                    toCreate.setPicUrl(cartVO.getPicUrl());
                    toCreate.setPrice(cartVO.getPrice());
                    toCreate.setProductId(cartVO.getSkuId());
                    toCreate.setSpecifications(cartVO.getSpecifications());

                    orderGoodsMapper.insertSelective(toCreate);
                }
                //将购物车中的商品设置为无效
                cartClient.clearUserCart(new ClearUserCartDTO(createOrderDTO.getUid()));
            }else{

            }
        } else {

        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    @Override
    public OrderVO orderDetail(Integer orderId) {
        MallOrderExample orderExample = new MallOrderExample();
        orderExample.createCriteria().andIdEqualTo(orderId);
        List<MallOrder> mallOrders = orderMapper.selectByExample(orderExample);
        assert mallOrders.size() == 1;
        MallOrder mallOrder = mallOrders.get(0);
        List<MallOrderGoods> mallOrderGoods = orderGoodsMapper.selectByOrderId(mallOrder.getId());
        List<ApiOrderGoodsVO> voList = new ArrayList<>();

        for (MallOrderGoods goods : mallOrderGoods) {
            ApiOrderGoodsVO goodsVO = new ApiOrderGoodsVO();
            BeanUtils.copyProperties(goods, goodsVO);
            voList.add(goodsVO);
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(mallOrder, orderVO);
        orderVO.setOrderGoodsList(voList);
        return orderVO;
    }
}
