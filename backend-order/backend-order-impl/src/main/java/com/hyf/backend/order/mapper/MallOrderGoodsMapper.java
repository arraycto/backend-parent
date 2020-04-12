package com.hyf.backend.order.mapper;

import com.hyf.backend.common.mybatis.mapper.BaseMapperWithPK;
import com.hyf.backend.order.dataobject.MallOrderGoods;
import com.hyf.backend.order.dataobject.MallOrderGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/12
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallOrderGoodsMapper extends BaseMapperWithPK<MallOrderGoods, MallOrderGoodsExample, Integer> {
    @Select("select * from mall_order_goods where order_id = #{orderId}")
    List<MallOrderGoods> selectByOrderId(@Param("orderId") Integer orderId);
}
