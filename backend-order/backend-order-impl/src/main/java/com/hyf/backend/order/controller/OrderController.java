package com.hyf.backend.order.controller;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.order.api.OrderApi;
import com.hyf.backend.order.dto.CreateOrderDTO;
import com.hyf.backend.order.service.OrderService;
import com.hyf.backend.order.vo.OrderVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class OrderController implements OrderApi {
    @Autowired
    private OrderService orderService;

    @Override
    public ResponseVO<OrderVO> submitOrder(CreateOrderDTO createOrderDTO) {
        String s = ContextHolder.getCurrentContext()
                .get(Constant.X_UID);
        Integer uid = null;
        if (StringUtils.isNotEmpty(s)) {
            uid = Integer.valueOf(s);
        }
        createOrderDTO.setUid(uid);
        return ResponseVO.ok(orderService.submitOrder(createOrderDTO));
    }

    @Override
    public ResponseVO<OrderVO> orderDetail(Integer orderId) {
        return ResponseVO.ok(orderService.orderDetail(orderId));
    }
}
