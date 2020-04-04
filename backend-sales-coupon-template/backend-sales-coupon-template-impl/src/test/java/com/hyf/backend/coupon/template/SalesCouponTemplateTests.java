package com.hyf.backend.coupon.template;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.coupon.template.admin.dto.AdminQueryCouponTemplateDTO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDOExample;
import com.hyf.backend.coupon.template.mapper.CouponTemplateDOMapper;
import com.hyf.backend.coupon.template.service.UserCouponCacheService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SalesCouponTemplateTests {
    @Autowired(required = false)
    private CouponTemplateDOMapper templateMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private UserCouponCacheService userCouponCacheService;

    @Test
    public void test1() {
        CouponTemplateDO salesCouponTemplateDO = new CouponTemplateDO();
        salesCouponTemplateDO.setIsAvailable(true);
        salesCouponTemplateDO.setIsExpired(false);
        salesCouponTemplateDO.setTitle("满50减20");
        salesCouponTemplateDO.setLogo("fdsafds");
        salesCouponTemplateDO.setDiscountCategory(CouponDiscountCategoryEnum.ZHEKOU);
        salesCouponTemplateDO.setCouponCount(100);
        salesCouponTemplateDO.setCreateUser(0);
        salesCouponTemplateDO.setTemplateKey("fdsafdsa");
        salesCouponTemplateDO.setDispatchUsers("");
        salesCouponTemplateDO.setExpirationCode(CouponTemplateExpirationEnum.GUDING);
        salesCouponTemplateDO.setExpirationGap(0);
        salesCouponTemplateDO.setExpirationDeadline(new Date());
        salesCouponTemplateDO.setManjianQuota(20);
        salesCouponTemplateDO.setDiscountBase(50);
        salesCouponTemplateDO.setEachGetLimitation(20);
//        salesCouponTemplateDO.setGoodsTypeLimitation("1,2,3,4,5");
//        salesCouponTemplateDO.setWeight("1,2,3");

        templateMapper.insertSelective(salesCouponTemplateDO);
//        templateMapper.insert(salesCouponTemplateDO);
    }

    @Test
    public void testUpdate() {
        CouponTemplateDO toUpdate = new CouponTemplateDO();
        toUpdate.setId(16L);
        toUpdate.setTitle("立减100");
        templateMapper.updateByPrimaryKeySelective(toUpdate);
    }

    @Test
    public void testSelectMapperHelpder() {
        CouponTemplateDOExample example = new CouponTemplateDOExample();
        CouponTemplateDOExample.Criteria criteria = example.createCriteria();
        AdminQueryCouponTemplateDTO queryCouponTemplateDTO = new AdminQueryCouponTemplateDTO();
        queryCouponTemplateDTO.setPageNo(1);
        queryCouponTemplateDTO.setPageSize(10);
        queryCouponTemplateDTO.setTitle("满100");
        queryCouponTemplateDTO
                .setOrderBy("coupon_count");
        queryCouponTemplateDTO.setCreateTimeStart(new Date());
        queryCouponTemplateDTO.setCreateTimeEnd(new Date());
        MapperHelper.selectPageByQuery(templateMapper, example, criteria, queryCouponTemplateDTO, queryCouponTemplateDTO.toPage());
    }

    @Test
    public void testSeriableList() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        String s = objectMapper.writeValueAsString(list);
//        System.out.println(s);
//
        List<Integer> integers = JSON.parseArray("[1,2,3,4]", Integer.class);
        System.out.println(integers);
    }

    @Test
    public void testSendMsg() {
        rocketMQTemplate.convertAndSend("test-topic:test-tag", "hello this is test msg payload tag!!!");
//        rocketMQTemplate.send
    }

    @Test
    public void testGetRedisPage() {
        for (int i = 0; i < 12; i++) {
            UserCouponBO userCouponBO = new UserCouponBO()
                    .setId(RandomUtils.nextLong(1, 100000))
                    .setUserId(1L)
                    .setGetTime(new DateTime().plusSeconds(i).toDate())
                    .setStatus(UserCouponStatusEnum.USABLE);
            userCouponCacheService.addCoupon(1L, UserCouponStatusEnum.USABLE.getCode(), Collections.singletonList(userCouponBO));
        }
    }

    @Test
    public void testGetRedisPage1() {
//        List<UserCouponBO> userCouponPage = userCouponCacheService.getUserCouponPage(1L, UserCouponStatusEnum.USABLE.getCode(), 1, 5);
//        System.out.println(userCouponPage);
    }

    @Test
    public void testConsumer() throws IOException {
        System.in.read();
    }

    static class C1 {
        public static Integer a = 1;
    }

    static class C2 extends C1 {
        public static Integer b = 2;

        static {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        System.out.println(C2.b);
    }
}
