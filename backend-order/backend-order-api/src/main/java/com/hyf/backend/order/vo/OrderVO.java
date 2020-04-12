package com.hyf.backend.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class OrderVO {
    private Integer id;

    private Integer userId;

    private String orderSn;

    private Short orderStatus;

    private Short aftersaleStatus;

    private String consignee;

    private String mobile;

    private String address;

    private String message;

    private BigDecimal goodsPrice;

    private BigDecimal freightPrice;

    private BigDecimal couponPrice;

    private BigDecimal integralPrice;

    private BigDecimal grouponPrice;

    private BigDecimal orderPrice;

    private BigDecimal actualPrice;

    private String payId;

    private Date payTime;

    private String shipSn;

    private String shipChannel;

    private Date shipTime;

    private BigDecimal refundAmount;

    private String refundType;

    private String refundContent;

    private Date refundTime;

    private Date confirmTime;

    private Short comments;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    List<ApiOrderGoodsVO> orderGoodsList;
}
