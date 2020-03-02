package com.hyf.backend.coupon.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyf.backend.coupon.template.entity.SalesCouponTemplate;
import com.hyf.backend.coupon.template.mapper.SalesCouponTemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesCouponTemplateTests {

    @Autowired
    private SalesCouponTemplateMapper couponTemplateMapper;

    @Test
    public void testAddCouponTemplate() {
        SalesCouponTemplate salesCouponTemplate = new SalesCouponTemplate();
        salesCouponTemplate.setAdminUserId(10L);
        salesCouponTemplate
                .setAvailable(1);
        salesCouponTemplate
                .setCouponCount(20);
//        salesCouponTemplate
        salesCouponTemplate.setCreateTime(LocalDateTime.now());
        salesCouponTemplate.setName("墨尔本线路优惠");
        couponTemplateMapper.insert(salesCouponTemplate);
    }

    @Test
    public void testSelect(){
        QueryWrapper<SalesCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "%墨尔本");
        SalesCouponTemplate salesCouponTemplate = couponTemplateMapper.selectOne(queryWrapper);
        System.out.println(salesCouponTemplate);
    }
    @Test
    public void testExecutors() {

    }
}
