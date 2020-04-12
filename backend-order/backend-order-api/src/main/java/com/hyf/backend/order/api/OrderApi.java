package com.hyf.backend.order.api;

import com.hyf.backend.order.dto.CreateOrderDTO;
import com.hyf.backend.order.vo.OrderVO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/order")
public interface OrderApi {
    @PostMapping("/submit")
    ResponseVO<OrderVO> submitOrder(@RequestBody CreateOrderDTO createOrderDTO);
    @GetMapping("/detail")
    ResponseVO<OrderVO> orderDetail(@RequestParam("orderId") Integer orderId);
}
