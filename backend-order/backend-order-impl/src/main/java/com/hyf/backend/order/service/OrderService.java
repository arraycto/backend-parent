package com.hyf.backend.order.service;

import com.hyf.backend.order.dto.CreateOrderDTO;
import com.hyf.backend.order.vo.OrderVO;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface OrderService {
    OrderVO submitOrder(CreateOrderDTO createOrderDTO);

    OrderVO orderDetail(Integer orderId);
}
